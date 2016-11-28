package com.modastylz.api.handlers.processors;

import com.modastylz.api.handlers.processors.messageProcessor.MessageProcessFactory;
import com.modastylz.api.handlers.processors.messageProcessor.MessageProcessor;
import com.modastylz.api.handlers.processors.messageProcessor.Processor;
import com.modastylz.api.handlers.processors.messageProcessor.ProcessorHandlerType;

import io.vertx.core.eventbus.Message;

public class ProcessorBuilder {
    private final Message<Object> message;

    private final ProcessorHandlerType handlerType;

    public ProcessorBuilder(ProcessorHandlerType handlerType, Message<Object> message) {
        this.message = message;
        this.handlerType = handlerType;
    }

    public Processor build() {
        return new MessageProcessor(MessageProcessFactory.getInstance(handlerType), message);
    }
}
