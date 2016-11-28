package com.modastylz.api.processors.command.executor.color;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;


public class ColorExecutorFactory {
	public static DBExecutor createColor(MessageContext messageContext) {
		return new CreateColorExecutor(messageContext);
	}

	public static DBExecutor updateColor(MessageContext messageContext) {
		return new UpdateColorExecutor(messageContext);
	}

	
	
	
	private ColorExecutorFactory() {
		throw new AssertionError();
	}


}
