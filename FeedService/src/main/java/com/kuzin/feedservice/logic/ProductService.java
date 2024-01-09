package com.kuzin.feedservice.logic;

import co.elastic.clients.elasticsearch._types.query_dsl.FieldAndFormat;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.kuzin.feedservice.dto.ProductDto;
import com.kuzin.feedservice.dto.SearchProductDto;
import com.kuzin.feedservice.entity.Product;
import com.kuzin.feedservice.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product insertProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();

        return productRepo.save(product);
    }


    public Product updateProduct(Product product, int id) {
        Product product1  = productRepo.findById(String.valueOf(id)).get();
        product1.setPrice(product.getPrice());
        return product1;
    }

    public void deleteProduct(int id ) {
        productRepo.deleteById(String.valueOf(id));
    }

}
