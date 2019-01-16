# spring依赖容器与dropwizard的集成

[![Build Status](https://travis-ci.org/jincs1414/spring-dropwizard.svg?branch=master)](https://travis-ci.org/jincs1414/spring-dropwizard)

[![maven model](https://jitpack.io/v/jincs1414/spring-dropwizard.svg)](https://jitpack.io/#jincs1414/spring-dropwizard)

通过在dropwizard中集成spring framework，获取spring framework提供的依赖注入
和bean生成功能，以及相应便捷的注解和包扫描功能

### 思路

- 首先创建spring容器的上下文，并进行相关bean的注册(就可以使用spring容器提供的相应功能了)
- 创建dropwizard的运行环境，并在创建过程中，将spring容器中
对应的resource bean注册到dropwizard的运行环境中
- 测试resource是否正常使用(http://127.0.0.1:8888/application/test/sayHello?name=1)

### 备注

- 运行服务时，通过运行参数配置，自定义配置文件`server dropwizard.yaml`

- 调用`SpringDropwizardApplication.star()`运行服务

- 采用 [https://jitpack.io](https://jitpack.io) 作为mavne模块的分发仓库
```xml
	<dependency>
	    <groupId>com.github.jincs1414</groupId>
	    <artifactId>spring-dropwizard</artifactId>
	    <version>master-SNAPSHOT</version>
	</dependency>
```


### 参考
[dropwizard文档](https://www.dropwizard.io/1.3.5/docs/getting-started.html)

[dropwizard基本配置](https://github.com/dropwizard/dropwizard/blob/master/dropwizard-example/example.yml)

[基于java的spring bean的注解配置](https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#beans-java)


