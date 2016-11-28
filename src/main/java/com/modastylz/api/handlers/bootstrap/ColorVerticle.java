package com.modastylz.api.handlers.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modastylz.api.handlers.constants.MessagebusEndpoints;
import com.modastylz.api.handlers.processors.ProcessorBuilder;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.ProcessorHandlerType;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class ColorVerticle extends AbstractVerticle{
    private static final Logger LOG = LoggerFactory.getLogger(ColorVerticle.class);

    public void start(Future<Void> voidFuture)throws Exception{
        final EventBus eb = vertx.eventBus();
        eb.consumer(MessagebusEndpoints.MBEP_COLOR, message -> {
            System.out.println("Recieved body" +message.body());
            vertx.executeBlocking(future -> {
                MessageResponse result =
                    new ProcessorBuilder(ProcessorHandlerType.COLOR, message).build().process();
                future.complete(result);
            }, res -> {
                MessageResponse result = (MessageResponse) res.result();
                message.reply(result.reply(), result.deliveryOptions());
            });

        }).completionHandler(result -> {
            if (result.succeeded()) {
                LOG.info("authentication end point ready to listen");
                voidFuture.complete();
            } else {
                LOG.error("Error registering the authentication handler. Halting the authentication machinery");
                voidFuture.fail(result.cause());
            }
        });
    }
    }

