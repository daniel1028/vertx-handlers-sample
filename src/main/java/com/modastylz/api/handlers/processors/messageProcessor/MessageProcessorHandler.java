package com.modastylz.api.handlers.processors.messageProcessor;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;

public interface MessageProcessorHandler {
    MessageResponse process(MessageContext messageContext);

}
