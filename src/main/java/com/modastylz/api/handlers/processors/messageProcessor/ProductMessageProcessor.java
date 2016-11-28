package com.modastylz.api.handlers.processors.messageProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modastylz.api.handlers.constants.CommandConstants;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.exceptions.InvalidRequestException;
import com.modastylz.api.handlers.processors.repositories.RepoFactory;

public final class ProductMessageProcessor implements MessageProcessorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ProductMessageProcessor.class);

	@Override
	public MessageResponse process(MessageContext messageContext) {
		MessageResponse result = null;
		switch (messageContext.command()) {
		case CommandConstants.CREATE_PRODUCT:
			result = RepoFactory.getProductRepo(messageContext).createProduct();
			break;
		case CommandConstants.UPDATE_PRODUCT:
			result = RepoFactory.getProductRepo(messageContext).updateProduct();
			break;
		case CommandConstants.DELETE_PRODUCT:
			result = RepoFactory.getProductRepo(messageContext).deleteProduct();
			break;
		case CommandConstants.FIND_PRODUCT:
			result = RepoFactory.getProductRepo(messageContext).findProduct();
			break;
		default:
			LOG.error("Invalid command type passed in, not able to handle");
			throw new InvalidRequestException();
		}
		return result;
	}

}
