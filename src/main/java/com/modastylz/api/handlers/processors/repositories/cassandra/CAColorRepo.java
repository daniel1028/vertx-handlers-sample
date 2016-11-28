package com.modastylz.api.handlers.processors.repositories.cassandra;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.transactions.CATransactionExecutor;
import com.modastylz.api.processors.command.executor.color.ColorExecutorFactory;

public class CAColorRepo implements ColorRepo {

	private final MessageContext messageContext;

	public CAColorRepo(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	
	@Override
	public MessageResponse createColor() {
		
		return CATransactionExecutor.executeTransaction(ColorExecutorFactory.createColor(messageContext));
		
	}

	@Override
	public MessageResponse updateColor() {
		return CATransactionExecutor.executeTransaction(ColorExecutorFactory.updateColor(messageContext));
	}

	@Override
	public MessageResponse findColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageResponse deleteColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
