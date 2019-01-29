package me.jincs.java.dropwizard.spring;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.jincs.java.dropwizard.configure.DropwizardConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jinchengsong on 2018/10/18.
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {
        "me.jincs.java.dropwizard"
})
public class SpringDropwizardApplication  extends Application<Configuration>
{

    private static final String BASE_COMMAND_ARG = "server dropwizard.yaml";
    private static boolean USE_DEFAULT_CONFIGURE = false;
    private static ApplicationContext springContext;
    private static Environment dropwizardEnvironment;

    public static ApplicationContext getSpringContext(){
        return springContext;
    }

    public static Environment getDropwizardEnvironment() {
        return dropwizardEnvironment;
    }

    public static void start(DropwizardConfig dopwizardConfigure)  {
        //创建spring容器，并注册bean
        Class[] classes = dopwizardConfigure.getSpringConfig().getJavaConfig();
        List<Class> classList = new ArrayList<>(Arrays.asList(classes));
        classList.add(SpringDropwizardApplication.class);
        springContext = new AnnotationConfigApplicationContext(classList.toArray(classes));
        //创建dropwizard的运行环境
        //默认配置
        String[] args = dopwizardConfigure.getDropwizardConfig();
        if(args == null || args.length == 0){
            args = BASE_COMMAND_ARG.split(" ");
            USE_DEFAULT_CONFIGURE = true;
        }
        try {
            springContext.getBean(SpringDropwizardApplication.class).run(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("启动dropwizard服务失败");
        }
    }

    @Override
     public void run(Configuration configure, Environment environment) throws Exception {
        dropwizardEnvironment = environment;
        registerResources(environment);
    }

    /**
     * 初始化环境配置
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
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
