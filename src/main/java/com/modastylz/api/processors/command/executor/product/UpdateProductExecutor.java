package com.modastylz.api.processors.command.executor.product;

import com.modastylz.api.handlers.processors.command.executor.DBExecutor;
import com.modastylz.api.handlers.processors.command.executor.MessageResponse;
import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.model.Product;

import io.vertx.core.json.JsonObject;

public final class UpdateProductExecutor implements DBExecutor {

    private final MessageContext messageContext;
    private JsonObject data = null;

    public UpdateProductExecutor(MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    @Override
    public void checkSanity() {
    	data = messageContext.requestBody();
    	
        
    }

    @Override
    public void validateRequest() {
        

    }

    @Override
    public MessageResponse executeRequest() {
    	Product product = new Product(data);
    	product.update("id", messageContext.requestParams().getString("id"));
        
    	return new MessageResponse.Builder().setContentTypeJson().setStatusNoOutput().successful().build();
    }

    @Override
    public boolean handlerReadOnly() {
        return true;
    }

}
