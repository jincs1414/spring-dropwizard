package me.jincs.java.dropwizard.app;

import me.jincs.java.dropwizard.configure.DropwizardConfig;
import me.jincs.java.dropwizard.spring.SpringDropwizardApplication;
import me.jincs.java.dropwizard.spring.configure.SpringConfigure;

public class Application {

    public static void main(String[] args) {
        SpringDropwizardApplication.start(new DropwizardConfig() {
            @Override
            public SpringConfigure getSpringConfig() {
                return new SpringConfigure() {
                    @Override
                    public Class[] getJavaConfig() {
                        return new Class[0];
                    }
                };
            }

            @Override
            public String[] getDropwizardConfig() {
                return new String[0];
            }
        });
    }
}
