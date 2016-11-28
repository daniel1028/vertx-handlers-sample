package com.modastylz.api.handlers.processors.repositories.cassandra.model;

import io.vertx.core.json.JsonObject;

public class Color extends CAModel{
	private static final String COLUMN_FAMILY = "color";

	private JsonObject data;

	public Color(JsonObject data) {
		this.data = data;
	}

	public static JsonObject find(String key, String keyValue) {
		return find(COLUMN_FAMILY, key, keyValue);
	}

	public static void delete(String key, String keyValue) {
		delete(COLUMN_FAMILY, key, keyValue);
	}

	@Override
	public JsonObject getData() {
		return data;
	}

	@Override
	public String getColumnFamily() {
		return COLUMN_FAMILY;
	}
}
