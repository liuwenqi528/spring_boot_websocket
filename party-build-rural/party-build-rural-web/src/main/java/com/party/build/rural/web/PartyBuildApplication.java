package com.party.build.rural.web;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.party.build.rural"})
@EnableTransactionManagement// 开启事务
@ConfigurationProperties(prefix="spring.datasource")
@MapperScan("com.party.build.rural.mapper")
public class PartyBuildApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PartyBuildApplication.class);
        ApplicationContext context = application.run();
    }
}
