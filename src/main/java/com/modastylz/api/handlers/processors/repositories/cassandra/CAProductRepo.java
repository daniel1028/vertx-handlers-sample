package com.modastylz.api.handlers.processors.repositories.cassandra;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.transactions.CATransactionExecutor;
import com.modastylz.api.processors.command.executor.product.ProductExecutorFactory;

public class CAProductRepo implements ProductRepo {
	private final MessageContext messageContext;

	public CAProductRepo(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public MessageResponse createProduct() {
		return CATransactionExecutor.executeTransaction(ProductExecutorFactory.createProduct(messageContext));
	}

	@Override
	public MessageResponse updateProduct() {
		return CATransactionExecutor.executeTransaction(ProductExecutorFactory.updateProduct(messageContext));
	}

	@Override
	public MessageResponse findProduct() {
		return CATransactionExecutor.executeTransaction(ProductExecutorFactory.findProduct(messageContext));
	}

	@Override
	public MessageResponse deleteProduct() {
		return CATransactionExecutor.executeTransaction(ProductExecutorFactory.deleteProduct(messageContext));
	}
}
