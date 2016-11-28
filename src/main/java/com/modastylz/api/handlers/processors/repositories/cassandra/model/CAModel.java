package com.modastylz.api.handlers.processors.repositories.cassandra.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Update.Assignments;
import com.modastylz.api.handlers.infra.CADataSourceRegistry;

import io.vertx.core.json.JsonObject;

abstract class CAModel {

	abstract protected JsonObject getData();
	

	abstract protected String getColumnFamily();

    private static final Logger LOG = LoggerFactory.getLogger(CAModel.class);

	private final static CADataSourceRegistry DATASOURCE = CADataSourceRegistry.getInstance();

	public void save() {
		JsonObject data = getData();
		Session session = DATASOURCE.getSession();
		StringBuffer query = new StringBuffer("INSERT INTO ");
		query.append(getColumnFamily());
		query.append("(");
		boolean intialized = false;
		for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
			if (intialized) {
				query.append(",");
			}
			query.append(entry.getKey());
			intialized = true;
		}
		query.append(") VALUES (");
		intialized = false;
		int count = 0;
		Object[] values = new Object[data.size()];
		for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
			System.out.println(entry);
			if (intialized) {
				query.append(",");
			}
			query.append("?");
			intialized = true;
			values[count++] = entry.getValue();
		}
		query.append(");");
		System.out.println(query);
		PreparedStatement statement = session.prepare(query.toString());
		BoundStatement boundStatement = new BoundStatement(statement);
		
		session.execute(boundStatement.bind(values));
	}

	public void update(String key, Object keyValue) {
		JsonObject data = getData();
		Session session = DATASOURCE.getSession();
		Assignments assignments = QueryBuilder.update(getColumnFamily()).with();
		for (Map.Entry<String, Object> entry : data.getMap().entrySet()) {
			assignments.and(QueryBuilder.set(entry.getKey(), entry.getValue()));
		}
		Statement update = assignments.where((QueryBuilder.eq(key, keyValue)));
		System.out.println(update);
		session.execute(update);
	}

	protected static void delete(String columnFamily, String key, Object KeyValue) {
		Session session = DATASOURCE.getSession();
		Statement delete = QueryBuilder.delete().from(columnFamily).where(QueryBuilder.eq(key, KeyValue));
		session.execute(delete);
	}

	protected static JsonObject find(String columnFamily, String key, Object keyValue) {
		JsonObject resultJson = null;
		Session session = DATASOURCE.getSession();
		Statement select = QueryBuilder.select().all().from(columnFamily).where(QueryBuilder.eq(key, keyValue));
		ResultSet results = session.execute(select);
		if (results != null) {
			resultJson = new JsonObject();
			Row row = results.one();
			List<Definition> definitions = row.getColumnDefinitions().asList();
			for (Definition definition : definitions) {
				Object value = row.getObject(definition.getName());
				if (value instanceof Date) {
					resultJson.put(definition.getName(), ((Date) value).toInstant());
				} else {
					resultJson.put(definition.getName(), value);
				}
			}
		}
		return resultJson;
	}
	
	
		
}
