package com.modastylz.api.handlers.bootstrap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modastylz.api.handlers.bootstrap.shutdown.Finalizer;
import com.modastylz.api.handlers.bootstrap.shutdown.Finalizers;
import com.modastylz.api.handlers.bootstrap.startup.Initializer;
import com.modastylz.api.handlers.bootstrap.startup.Initializers;
import com.modastylz.api.handlers.constants.ConfigConstants;

public class BootstrapVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapVerticle.class);

    @Override
    public void start(Future<Void> voidFuture) throws Exception {
        vertx.executeBlocking(blockingFuture -> {
            startApplication();
            blockingFuture.complete();
        }, future -> {
            if (future.succeeded()) {
                deployVerticles(voidFuture);
            } else {
                voidFuture.fail("Not able to initialize the modastylz api handlers machiners properly");
            }
        });
    }

    private void deployVerticles(Future<Void> voidFuture) {
        LOG.debug("Starting to deploy other verticles...");
        final JsonArray verticlesList = config().getJsonArray(ConfigConstants.VERTICLES_DEPLOY_LIST);
        @SuppressWarnings("unchecked")
        final CompletableFuture<Void>[] resultFutures = new CompletableFuture[verticlesList.size()];

        for (int i = 0; i < verticlesList.size(); i++) {
            final String verticleName = verticlesList.getString(i);
            // Note that verticle name should be starting with "service:" prefix
            if (verticleName != null && !verticleName.isEmpty()) {
                LOG.debug("Starting verticle: {}", verticleName);

                final CompletableFuture<Void> deployFuture = new CompletableFuture<>();
                resultFutures[i] = deployFuture;

                vertx.deployVerticle(verticleName, res -> {
                    if (res.succeeded()) {
                        deployFuture.complete(null);
                        LOG.info("Deployment id is: " + res.result() + " for verticle: " + verticleName);
                    } else {
                        deployFuture.completeExceptionally(res.cause());
                        LOG.info("Deployment failed!");
                    }
                });
            } else {
                LOG.error("Invalid verticle name specified in configuration. Aborting");
                throw new IllegalArgumentException("Invalid verticle name specified in configuration. Aborting.");
            }
        }
        vertx.executeBlocking(future -> {
            try {
                CompletableFuture.allOf(resultFutures).join();
                future.complete();
            } catch (CompletionException e) {
                e.printStackTrace();
                future.fail(e.getCause());
            }

        }, blockingResult -> {
            if (blockingResult.succeeded()) {
                LOG.info("Deployment successful");
                voidFuture.complete();
            } else {
                LOG.error("Error deploying verticles. Shutting down.");
                voidFuture.fail(blockingResult.cause());
            }
        });
    }

    @Override
    public void stop() throws Exception {
        shutDownApplication();
        super.stop();
    }

    private void startApplication() {
        Initializers initializers = new Initializers();
        try {
            for (Initializer initializer : initializers) {
                initializer.initializeComponent(vertx, config());
            }
        } catch (IllegalStateException ie) {
            LOG.error("Error initializing application", ie);
            Runtime.getRuntime().halt(1);
        }
    }

    private void shutDownApplication() {
        Finalizers finalizers = new Finalizers();
        for (Finalizer finalizer : finalizers) {
            finalizer.finalizeComponent();
        }
    }

}
