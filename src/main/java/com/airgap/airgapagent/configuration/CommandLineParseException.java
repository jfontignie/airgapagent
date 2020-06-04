package com.airgap.airgapagent.configuration;

/**
 * com.airgap.airgapagent.configuration
 * Created by Jacques Fontignie on 6/4/2020.
 */
public class CommandLineParseException extends Exception {

    public CommandLineParseException(String message) {
        super(message);
    }

    public CommandLineParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
