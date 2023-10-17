package com.example.backend_paychex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class Backend_PaychexApplication {

    public static void main(String[] args) {
        SpringApplication.run(Backend_PaychexApplication.class, args);
    }

}
