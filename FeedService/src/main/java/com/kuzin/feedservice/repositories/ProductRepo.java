package com.kuzin.feedservice.repositories;

import com.kuzin.feedservice.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepo extends ElasticsearchRepository<Product,String> {

}