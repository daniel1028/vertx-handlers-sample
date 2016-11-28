package com.modastylz.api.handlers.constants;

/**
 * Constant definition that are used to read configuration
 */
final public class ConfigConstants {

    public static final String PORT = "port";
    public static final String HOST = "host";
    public static final String VERTICLES_DEPLOY_LIST = "verticles.deploy.list";

    private ConfigConstants() {
		throw new AssertionError();
	}
}
