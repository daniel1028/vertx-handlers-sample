package com.modastylz.api.handlers.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.modastylz.api.handlers.bootstrap.shutdown.Finalizer;
import com.modastylz.api.handlers.bootstrap.startup.Initializer;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class CADataSourceRegistry implements Initializer, Finalizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(CADataSourceRegistry.class);

	private Session session;

	private CADataSourceRegistry() {
	}

	public static CADataSourceRegistry getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void initializeComponent(Vertx vertx, JsonObject config) {
		try {
			JsonObject cassandraConfig = config.getJsonObject("cassandra");

			Cluster cluster = Cluster.builder().withClusterName(cassandraConfig.getString("cluster"))
					.addContactPoints(cassandraConfig.getString("hosts").split(","))
					.withRetryPolicy(DefaultRetryPolicy.INSTANCE).withPoolingOptions(poolingOptions())
					.withReconnectionPolicy(new ExponentialReconnectionPolicy(1000, 30000)).build();
			setSession(cluster.connect(cassandraConfig.getString("keyspace")));
		} catch (Exception e) {
			LOGGER.error("Error while initializing cassandra : {}", e);
			throw e;
		}
	}

	@Override
	public void finalizeComponent() {
		if (getSession() != null && !getSession().isClosed()) {
			getSession().close();
		}
	}

	private static PoolingOptions poolingOptions() {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, 4, 4).setConnectionsPerHost(HostDistance.REMOTE, 2, 2)
				.setMaxRequestsPerConnection(HostDistance.LOCAL, 4096)
				.setMaxRequestsPerConnection(HostDistance.REMOTE, 512).setIdleTimeoutSeconds(30)
				.setPoolTimeoutMillis(15000);
		return poolingOptions;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	private static class Holder {
		private static final CADataSourceRegistry INSTANCE = new CADataSourceRegistry();
	}

}