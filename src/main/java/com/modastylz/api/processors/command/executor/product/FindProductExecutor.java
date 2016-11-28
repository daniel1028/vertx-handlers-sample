package com.modastylz.api.processors.command.executor.product;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.model.Product;

import io.vertx.core.json.JsonObject;

public final class FindProductExecutor implements DBExecutor {

	private MessageContext messageContext;
	private JsonObject data = null ;
	
	public FindProductExecutor(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public void checkSanity() {
		data = messageContext.requestParams().copy();

	}

	@Override
	public void validateRequest() {

	}

	@Override
	public MessageResponse executeRequest() {
		Product product = new Product(data);
		JsonObject result = Product.find("id", product.getData().getString("id"));
		return new MessageResponse.Builder().setContentTypeJson().setStatusOkay().setResponseBody(result).successful().build();
	}

	@Override
	public boolean handlerReadOnly() {
		return false;
	}
}
