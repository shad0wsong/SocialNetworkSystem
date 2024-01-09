package com.kuzin.feedservice.controllers;

import com.kuzin.feedservice.dto.ProductDto;
import com.kuzin.feedservice.entity.Product;
import com.kuzin.feedservice.logic.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apis")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/findAll")
    Iterable<Product> findAll(){
        return productService.getProducts();

    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody ProductDto productDto){
        return productService.insertProduct(productDto);
    }



}
