package com.modastylz.api.processors.command.executor.product;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;

public final class ProductExecutorFactory {

	public static DBExecutor createProduct(MessageContext messageContext) {
		return new CreateProductExecutor(messageContext);
	}
	
	public static DBExecutor updateProduct(MessageContext messageContext) {
		return new UpdateProductExecutor(messageContext);
	}

	public static DBExecutor findProduct(MessageContext messageContext) {
		return new FindProductExecutor(messageContext);
	}
	
	
	public static DBExecutor deleteProduct(MessageContext messageContext) {
		return new DeleteProductExecutor(messageContext);
	}

	private ProductExecutorFactory() {
		throw new AssertionError();
	}
}
