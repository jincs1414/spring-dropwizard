package me.jincs.java.dropwizard.spring;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.jincs.java.dropwizard.spring.configure.DropwizardConfigure;
import me.jincs.java.dropwizard.spring.configure.SpringBeanConfigure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.Path;
import java.io.File;
import java.util.Arrays;

/**
 * Created by jinchengsong on 2018/10/18.
 */
public class SpringDropwizardApplication  extends Application<DropwizardConfigure>
{

    private static final String BASE_COMMAND_ARG = "server /me/jincs/java/dropwizard/spring/configure/dropwizard.yaml";
    private static boolean USE_DEFAULT_CONFIGURE = false;
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

    public static void main(String[] args) {
        start(args);
    }
    public static void start(String[] args)  {
        //创建spring容器，并注册bean
        springContext = new AnnotationConfigApplicationContext(SpringBeanConfigure.class);
        //创建dropwizard的运行环境
        //默认配置
        if(args == null || args.length == 0){
            args = BASE_COMMAND_ARG.split(" ");
            USE_DEFAULT_CONFIGURE = true;
        }
        try {
            new SpringDropwizardApplication().run(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("启动dropwizard服务失败");
        }
    }



    @Override
     public void run(DropwizardConfigure configure, Environment environment) throws Exception {
        dopwizardConfigure = configure;
        dropwizardEnvironment = environment;
        registerResources(environment);
    }

    /**
     * 初始化环境配置
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<DropwizardConfigure> bootstrap) {
        if(USE_DEFAULT_CONFIGURE){
            bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        }
        super.initialize(bootstrap);
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
