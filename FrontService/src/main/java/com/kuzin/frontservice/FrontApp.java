package com.kuzin.frontservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FrontApp {
    public static void main(String[] args) {
        SpringApplication.run(FrontApp.class);
    }
}