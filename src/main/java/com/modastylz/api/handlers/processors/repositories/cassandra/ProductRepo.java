package com.modastylz.api.handlers.processors.repositories.cassandra;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;

public interface ProductRepo {

	MessageResponse createProduct();

	MessageResponse updateProduct();

	MessageResponse findProduct();

	MessageResponse deleteProduct();

}
