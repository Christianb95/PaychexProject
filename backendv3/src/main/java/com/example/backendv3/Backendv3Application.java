package com.example.backendv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class Backendv3Application {

    public static void main(String[] args) {
        SpringApplication.run(Backendv3Application.class, args);
    }

}
