package com.modastylz.api.processors.command.executor.color;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.model.Color;

import io.vertx.core.json.JsonObject;

public class UpdateColorExecutor implements DBExecutor {

	private final MessageContext messageContext;
	private JsonObject data = null;
	
	public UpdateColorExecutor(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	@Override
	public void checkSanity() {
    	data = messageContext.requestBody();
    	System.out.println("value "+data);
	}

	@Override
	public void validateRequest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageResponse executeRequest() {
		Color color = new Color(data);
		color.update("id", messageContext.requestParams().getString("id"));
    	return new MessageResponse.Builder().setContentTypeJson().setStatusNoOutput().successful().build();
	}

	@Override
	public boolean handlerReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

}
