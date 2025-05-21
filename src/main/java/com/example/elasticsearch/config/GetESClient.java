package com.example.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GetESClient {

    //ElasticsearchClient
    //This class is a high-level client for communicating with Elasticsearch.
    //It provides simplified features for operations such as storing, searching, and deleting documents.
    //This client uses a "Transport" to send requests.
    //RestClientBuilder : This class is responsible for building a low-level REST Client to communicate directly with Elasticsearch.
    //You can use this class to specify which host and port to connect to and which protocol to use (such as HTTP or HTTPS).
    //RestClientBuilder.HttpClientConfigCallback
    //This is an interface that allows you to configure more settings on HttpClient.
    //For example, you can set username/password for authentication or certificate for secure communication (SSL).
    //RestClientTransport
    //This class sits between the low-level RestClient and the high-level ElasticsearchClient.
    //It is responsible for converting data to JSON format and transferring requests from the high-level client to the low-level client.
    //To do this, it uses a JSON Mapper such as JacksonJsonpMapper.

    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.8.226", 9200, "http"));
        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback = new HttpClientConfigImpl();
        builder.setHttpClientConfigCallback(httpClientConfigCallback);
        RestClient restClient = builder.build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(restClientTransport);
    }

}
