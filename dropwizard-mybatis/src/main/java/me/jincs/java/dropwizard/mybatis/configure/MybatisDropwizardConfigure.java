package me.jincs.java.dropwizard.mybatis.configure;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class MybatisDropwizardConfigure {

    public MybatisDropwizardConfigure() {
        Properties mybatisProperties = new Properties();
        try {
            mybatisProperties.load(MybatisDropwizardConfigure.class.getClassLoader().getResourceAsStream("mybatis.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("未找到mybatis的配置文件");
        }

        mybatisMapperLocator = mybatisProperties.getProperty("mybatis.MapperLocator");
        jdbcUsername = mybatisProperties.getProperty("jdbc.username");
        jdbcPassword = mybatisProperties.getProperty("jdbc.password");
        jdbcurl = mybatisProperties.getProperty("jdbc.url");
        jdbcDriverClassName = mybatisProperties.getProperty("jdbc.driverClassName");
    }

    private  String mybatisMapperLocator;
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcurl;
    private String jdbcDriverClassName;

    @Bean
    public DataSource dataSource(){
        DruidDataSource druidDataSource =  new DruidDataSource();
        druidDataSource.setUsername(jdbcUsername);
        druidDataSource.setPassword(jdbcPassword);
        druidDataSource.setUrl(jdbcurl);
        druidDataSource.setDriverClassName(jdbcDriverClassName);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        //设置mybatis日志
        sqlSessionFactory.getConfiguration().setLogImpl(Log4j2Impl.class);
        return sqlSessionFactory;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(mybatisMapperLocator);
        return mapperScannerConfigurer;
    }

}
