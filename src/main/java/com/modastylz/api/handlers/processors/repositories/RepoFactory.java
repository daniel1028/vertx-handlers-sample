package com.modastylz.api.handlers.processors.repositories;

import com.modastylz.api.handlers.processors.messageProcessor.MessageContext;
import com.modastylz.api.handlers.processors.repositories.cassandra.CARepoFactory;
import com.modastylz.api.handlers.processors.repositories.cassandra.ColorRepo;
import com.modastylz.api.handlers.processors.repositories.cassandra.ProductRepo;

public final class RepoFactory {

	public static ColorRepo getColorRepo(MessageContext messageContext) {
        return CARepoFactory.getColorRepo(messageContext);
    }
	
    public static ProductRepo getProductRepo(MessageContext messageContext) {
        return CARepoFactory.getProductRepo(messageContext);
    }

    private RepoFactory() {
        throw new AssertionError();
    }
}
