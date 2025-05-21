package com.example.elasticsearch.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "employee")
public class Employee {

    @Id
    private Long id;
    private String name;
    private String department;
    private double salary;;

}
