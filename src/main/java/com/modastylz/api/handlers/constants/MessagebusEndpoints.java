package com.modastylz.api.handlers.constants;

final public class MessagebusEndpoints {
    
    public static final String MBEP_AUTH = "com.modastylz.api.message.bus.auth";
    public static final String MBEP_PRODUCT = "com.modastylz.api.message.bus.product";
    public static final String MBEP_COLOR= "com.modastylz.api.message.bus.color";

    private MessagebusEndpoints() { 
    	throw new AssertionError();
    }
}
