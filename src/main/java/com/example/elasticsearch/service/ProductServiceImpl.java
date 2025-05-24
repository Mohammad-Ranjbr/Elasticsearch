package com.example.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.elasticsearch.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ElasticsearchClient elasticsearchClient;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public Product createProduct(Product product) {
        try{
            elasticsearchClient.index(i -> // A method for sending a request to index a document.
                    i.index("products-002") // Specifies that the document be stored in the index "products-002".
                            .id(product.getId()) // The product ID is used as _id in ES.
                            .document(product)); // The Product object itself is stored as a document.
            return product;
        } catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<Product> getAllProducts() throws IOException {
        // A hit in Elasticsearch means a document found as a result of a search and matching the query criteria.
        SearchRequest searchRequest = new SearchRequest.Builder().index("products-002").build(); // A SearchRequest is created for the index products-002.
        SearchResponse<Product> response = elasticsearchClient.search(searchRequest, Product.class); // Using elasticsearchClient, the search request is executed.
        List<Hit<Product>> hits = response.hits().hits(); // Each Hit<Product> is a matched document from Elasticsearch.
        List<Product> products = new ArrayList<>();
        for (Hit<Product> hit : hits) {
            Product product = hit.source(); // Using .source(), the original content (Product) is extracted from each hit.
            products.add(product);
        }
        return products;
    }

    @Override
    public Product getProductById(String id) throws IOException {
        GetRequest getRequest = new GetRequest.Builder().index("products-002").id(id).build();
        GetResponse<Product> response = elasticsearchClient.get(getRequest, Product.class);
        if(response.found()){
            return  response.source();
        } else {
            return null;
        }
    }

    @Override
    public Product updateProduct(String id, Product product) throws IOException {
        UpdateRequest<Product, Product> updateRequest = new UpdateRequest.Builder<Product, Product>().index("products-002").id(id).doc(product).build();
        UpdateResponse<Product> updateResponse = elasticsearchClient.update(updateRequest, Product.class);
        if(updateResponse.result().equals(Result.Updated) || updateResponse.result().equals(Result.Created)){
            return product;
        } else {
            return null;
        }
    }



}
