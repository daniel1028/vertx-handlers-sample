package com.modastylz.api.handlers.processors.repositories.cassandra;

import com.modastylz.api.handlers.processors.command.executor.MessageResponse;

public interface ColorRepo {
	MessageResponse createColor();

	MessageResponse updateColor();

	MessageResponse findColor();

	MessageResponse deleteColor();


}
