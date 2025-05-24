package com.example.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
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
        GetRequest getRequest = new GetRequest.Builder().index("products-002").id(id).build(); // Creating a request to receive a document with a specific ID
        GetResponse<Product> response = elasticsearchClient.get(getRequest, Product.class);
        if(response.found()){ // Check if the document is found or not
            return  response.source(); // // If found, returns the Product object
        } else {
            return null;
        }
    }

    @Override
    public Product updateProduct(String id, Product product) throws IOException {
        // Create an update request by specifying the index, document ID, and new document.
        // UpdateRequest<TDocument, TPartialDocument>
        // TDocument The main type of document stored in Elasticsearch that you want to read or work with.
        // TPartialDocument The document type you want to update the document with (i.e. part of the document or doc(...))
        // Can they be different? Yes. If, for example, we only update a part of the document, we can use a separate DTO or class.
        UpdateRequest<Product, Product> updateRequest = new UpdateRequest.Builder<Product, Product>().index("products-002").id(id).doc(product).build();
        UpdateResponse<Product> updateResponse = elasticsearchClient.update(updateRequest, Product.class);
        // Because if the previous document does not exist, a new document may be created.
        if(updateResponse.result().equals(Result.Updated) || updateResponse.result().equals(Result.Created)){
            return product;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteProduct(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest.Builder().index("products-002").id(id).build();
        DeleteResponse deleteResponse = elasticsearchClient.delete(deleteRequest);
        return deleteResponse.result().equals(Result.Deleted);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("products-002")
                .query(q ->
                        q.match(t ->
                                t.field("category")
                                        .query(category)))
                .build();
        SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);
        List<Hit<Product>> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for (Hit<Product> hit : hits) {
            Product product = hit.source();
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByPriceRange(double min, double max) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("products-002")
                .query(q ->
                        q.range(t ->
                                t.field("price")
                                        .gte(JsonData.of(min))
                                        .lte(JsonData.of(max))))
                .build();
        SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);
        List<Hit<Product>> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for (Hit<Product> hit : hits) {
            Product product = hit.source();
            products.add(product);
        }
        return products;
    }

}
