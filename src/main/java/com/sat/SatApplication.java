package com.sat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatApplication.class, args);
    }

}
