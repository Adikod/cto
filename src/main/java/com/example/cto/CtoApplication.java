package com.example.cto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CtoApplication.class, args);
        log.info("СТО service stared");
    }
}
