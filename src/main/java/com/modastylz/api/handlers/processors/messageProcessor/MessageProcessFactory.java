package com.modastylz.api.handlers.processors.messageProcessor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MessageProcessFactory {

    private static final Map<ProcessorHandlerType, MessageProcessorHandler> instances = new HashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessFactory.class);

    public static MessageProcessorHandler getInstance(ProcessorHandlerType handlerType) {
        MessageProcessorHandler handler = instances.get(handlerType);
        if (handler == null) {
            synchronized (MessageProcessFactory.class) {
                if (handlerType.equals(ProcessorHandlerType.PRODUCT)) {
                    handler = new ProductMessageProcessor();
                } else if(handlerType.equals(ProcessorHandlerType.COLOR)){
                	handler = new ColorMessageProcessor();
                }else {
                    LOG.debug("None of the handlers matched, looks like invalid handler type.");
                }
            }
            instances.put(handlerType, handler);
        }

        return handler;

    }
}
