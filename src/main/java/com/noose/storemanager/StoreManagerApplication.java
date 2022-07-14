package com.noose.storemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StoreManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreManagerApplication.class, args);
    }
}
