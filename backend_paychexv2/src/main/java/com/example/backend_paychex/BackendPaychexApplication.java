package com.example.backend_paychex;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }) //remove exclude = {DataSourceAutoConfiguration.class } when ready
public class BackendPaychexApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPaychexApplication.class, args);
    }

}
