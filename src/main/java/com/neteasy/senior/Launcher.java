package com.neteasy.senior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class Launcher {
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
