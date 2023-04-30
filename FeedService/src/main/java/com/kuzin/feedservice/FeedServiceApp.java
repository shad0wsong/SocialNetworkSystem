package com.kuzin.feedservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FeedServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(FeedServiceApp.class);
    }
}
