package com.service.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ApplicationConfig.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // basic initing of spring boot
        SpringApplication.run(Application.class, args);
    }
}