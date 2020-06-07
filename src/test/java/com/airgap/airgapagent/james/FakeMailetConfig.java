package com.airgap.airgapagent.james;

import org.apache.mailet.MailetConfig;
import org.apache.mailet.MailetContext;

import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;

/**
 * com.airgap.airgapagent.james
 * Created by Jacques Fontignie on 6/7/2020.
 */
public class FakeMailetConfig implements MailetConfig {

    private final String mailetName;
    private final MailetContext mailetContext;
    private final Properties properties;

    private FakeMailetConfig(String mailetName, MailetContext mailetContext, Properties properties) {
        this.mailetName = mailetName;
        this.mailetContext = mailetContext;
        this.properties = properties;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getInitParameter(String name) {
        return properties.getProperty(name);
    }

    @Override
    public Iterator<String> getInitParameterNames() {
        return properties.stringPropertyNames().iterator();
    }

    @Override
    public MailetContext getMailetContext() {
        return mailetContext;
    }

    @Override
    public String getMailetName() {
        return mailetName;
    }

    public static class Builder {

        private static final String DEFAULT_MAILET_NAME = "A Mailet";
        private final Properties properties;
        private Optional<String> mailetName;
        private Optional<MailetContext> mailetContext;

        private Builder() {
            mailetName = Optional.empty();
            mailetContext = Optional.empty();
            properties = new Properties();
        }

        public Builder mailetName(String mailetName) {
            this.mailetName = Optional.ofNullable(mailetName);
            return this;
        }

        public Builder mailetContext(MailetContext mailetContext) {
            this.mailetContext = Optional.ofNullable(mailetContext);
            return this;
        }

        public Builder mailetContext(FakeMailContext.Builder mailetContext) {
            return mailetContext(mailetContext.build());
        }

        public Builder setProperty(String key, String value) {
            this.properties.setProperty(key, value);
            return this;
        }

        public FakeMailetConfig build() {
            return new FakeMailetConfig(mailetName.orElse(DEFAULT_MAILET_NAME),
                    mailetContext.orElse(FakeMailContext.defaultContext()),
                    properties);
        }
    }
}
