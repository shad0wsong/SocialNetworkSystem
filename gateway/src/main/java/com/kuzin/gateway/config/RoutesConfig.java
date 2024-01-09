package com.kuzin.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("posts", r -> r.path("/post/**")
                        .uri("lb://FEEDSERVICE"))
                .route("hashtags", r -> r.path("/hashtag/**")
                        .uri("lb://FEEDSERVICE"))
                .route("redisPosts", r -> r.path("/redis/**")
                        .uri("lb://FEEDSERVICE"))
                .route("posts", r -> r.path("/auth/**")
                        .uri("lb://AUTHSERVICE"))
                .build();
    }
}
