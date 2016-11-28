package com.modastylz.api.handlers.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modastylz.api.handlers.constants.MessageConstants;
import com.modastylz.api.handlers.constants.MessagebusEndpoints;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class AuthVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(AuthVerticle.class);

    @Override
    public void start(Future<Void> voidFuture) throws Exception {
        final EventBus eb = vertx.eventBus();
        eb.consumer(MessagebusEndpoints.MBEP_AUTH, message -> {
            LOG.debug("Received message: " + message.body());
            vertx.executeBlocking(future -> {
                future.complete(null);
            }, res -> {
                if (res.result() != null) {
                    JsonObject result = (JsonObject) res.result();
                    DeliveryOptions options = new DeliveryOptions()
                        .addHeader(MessageConstants.MSG_OP_STATUS, MessageConstants.MSG_OP_STATUS_SUCCESS);
                    message.reply(result, options);
                } else {
                    message.reply(null);
                }
            });

        }).completionHandler(result -> {
            if (result.succeeded()) {
                LOG.info("Auth end point ready to listen");
                voidFuture.complete();
            } else {
                LOG.error("Error registering the auth handler. Halting the Auth machinery");
                voidFuture.fail(result.cause());
            }
        });
    }
}
