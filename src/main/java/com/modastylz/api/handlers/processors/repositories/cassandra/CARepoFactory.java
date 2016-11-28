package com.modastylz.api.handlers.processors.repositories.cassandra;

import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;

public final class CARepoFactory {

	public static ProductRepo getProductRepo(MessageContext messageContext) {
		return new CAProductRepo(messageContext);
	}
	public static ColorRepo getColorRepo(MessageContext messageContext) {
		return new CAColorRepo(messageContext);
	}

	private CARepoFactory() {
		throw new AssertionError();
	}
}
