package me.jincs.java.dropwizard.spring;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import me.jincs.java.dropwizard.spring.configure.DropwizardConfigure;
import me.jincs.java.dropwizard.spring.configure.SpringBeanConfigure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.Path;

/**
 * Created by jinchengsong on 2018/10/18.
 */
public class SpringDropwizardApplication  extends Application<DropwizardConfigure>
{

    private static ApplicationContext springContext;
    private static DropwizardConfigure dopwizardConfigure;
    private static Environment dropwizardEnvironment;

    public static ApplicationContext getSpringContext(){
        return springContext;
    }

    public static DropwizardConfigure getDopwizardConfigure() {
        return dopwizardConfigure;
    }

    public static Environment getDropwizardEnvironment() {
        return dropwizardEnvironment;
    }

    public static void main(String[] args) throws Exception {
        //创建spring容器，并注册bean
        springContext = new AnnotationConfigApplicationContext(SpringBeanConfigure.class);
        //创建dropwizard的运行环境
        new SpringDropwizardApplication().run(args);
    }

    @Override
    public void run(DropwizardConfigure configure, Environment environment) throws Exception {
        dopwizardConfigure = configure;
        dropwizardEnvironment = environment;
        registerResources(environment);
    }

    /**
     * 从spring的bean中获取并注册请求资源
     * （bean对应的类必须加了@Path注解，因为这个规则我们才能确定它是resource）
     * @param environment
     */
    private void registerResources(Environment environment) {
        //注册请求资源
        String[] resourecBeanNames = springContext.getBeanNamesForAnnotation(Path.class);
        for(String name:resourecBeanNames){
            environment.jersey().register(springContext.getBean(name));
        }
    }
}
