package com.modastylz.api.processors.command.executor.product;

import java.util.Map.Entry;
import java.util.UUID;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.model.Product;

import io.vertx.core.json.JsonObject;

public final class CreateProductExecutor implements DBExecutor {

	private final MessageContext messageContext;

	private JsonObject data = null;
	JsonObject valuesObject = new JsonObject();


	public CreateProductExecutor(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public void checkSanity() {
		
		data = messageContext.requestBody().copy();
		data.put("id", UUID.randomUUID().toString());
	}
		

	@Override
	public void validateRequest() {

	}

	@Override
	public MessageResponse executeRequest() {
		Product product = new Product(data);
		product.save();
		return new MessageResponse.Builder().setContentTypeJson().setStatusCreated().successful().build();
	}

	@Override
	public boolean handlerReadOnly() {
		return false;
	}
}
