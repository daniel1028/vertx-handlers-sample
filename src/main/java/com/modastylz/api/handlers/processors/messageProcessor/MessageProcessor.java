package com.modastylz.api.handlers.processors.messageProcessor;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.exceptions.InvalidRequestException;

public class MessageProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorHandler.class);

    private final Message<Object> message;

    private final MessageProcessorHandler handler;

    public MessageProcessor(MessageProcessorHandler handler, Message<Object> message) {
        this.message = message;
        this.handler = handler;
    }

    @Override
    public MessageResponse process() {
        MessageResponse result = null;
        try {
            if (message == null || !(message.body() instanceof JsonObject)) {
                LOG.error("Invalid message received, either null or body of message is not JsonObject ");
                throw new InvalidRequestException();
            }
            MessageContext messageContext = new MessageContextHolder(message);
            result = handler.process(messageContext);
            
            return result;
        } catch (Throwable throwable) {
            LOG.warn("Caught unexpected exception here", throwable);
            return new MessageResponse.Builder().setThrowable(throwable).build();
        }
    }

}
