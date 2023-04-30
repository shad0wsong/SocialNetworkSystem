package com.kuzin.authservice;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApp.class);
    }

}

