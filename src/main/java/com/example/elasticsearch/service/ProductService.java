package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Product;

import java.io.IOException;

public interface ProductService {

    Product createProduct(Product product);
    Iterable<Product> getAllProducts() throws IOException;
    Product getProductById(String id) throws IOException;
    Product updateProduct(String id, Product product) throws IOException;

}
