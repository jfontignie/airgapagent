package com.airgap.airgapagent.configuration;

/**
 * com.airgap.airgapagent
 * Created by Jacques Fontignie on 6/3/2020.
 */
public class AirgapConfiguration {

    private final Action action;
    private final OutputConfiguration output;

    public AirgapConfiguration(Action action, OutputConfiguration output) {
        this.action = action;
        this.output = output;
    }

    Action getAction() {
        return action;
    }

    OutputConfiguration getOutput() {
        return output;
    }
}
