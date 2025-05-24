package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    Iterable<Product> getAllProducts() throws IOException;
    Product getProductById(String id) throws IOException;
    Product updateProduct(String id, Product product) throws IOException;
    boolean deleteProduct(String id) throws IOException;
    List<Product> getProductsByCategory(String category) throws IOException;
    List<Product> getProductsByPriceRange(double min, double max) throws IOException;

}
