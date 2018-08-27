package com.party.build.rural.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.party.build.rural"})
public class PartyBuildApplication2 {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PartyBuildApplication2.class);
        ApplicationContext context = application.run();
    }
}
