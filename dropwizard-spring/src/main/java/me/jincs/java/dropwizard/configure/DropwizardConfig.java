package me.jincs.java.dropwizard.configure;

import me.jincs.java.dropwizard.spring.configure.SpringConfigure;

public interface DropwizardConfig {
    /**
     * 获取模块的spring的javaConfig配置
     * @return
     */
    SpringConfigure getSpringConfig();

    /**
     * 获取Dropwizard自身的配置
     * @return
     */
    String[] getDropwizardConfig();
}
