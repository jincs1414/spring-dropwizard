package me.jincs.java.dropwizard.spring.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinchengsong on 2018/10/18.
 */
@Configuration
@ComponentScan(basePackages = "me.jincs.java.dropwizard.spring.resources")
public class SpringBeanConfigure {

    @Bean
    public Map defaultUserMap(){
        Map m = new HashMap();
        m.put("name","hah");
        return m;
    }
}
