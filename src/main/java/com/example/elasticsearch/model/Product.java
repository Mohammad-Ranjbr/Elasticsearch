package com.example.elasticsearch.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;

}
