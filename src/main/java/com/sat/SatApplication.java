package com.sat;

import com.sat.auth.domain.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class SatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatApplication.class, args);
    }

}
