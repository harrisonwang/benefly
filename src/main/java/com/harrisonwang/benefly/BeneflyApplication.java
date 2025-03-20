package com.harrisonwang.benefly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeneflyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeneflyApplication.class, args);
    }

}
