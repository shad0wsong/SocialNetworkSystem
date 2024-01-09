package com.kuzin.feedservice.entity;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "products")
@Builder
public class Product {

    @Id
    @GeneratedValue
    private String id;

    private String name;
    private String description;

    private int quantity;

    private double price;
}
