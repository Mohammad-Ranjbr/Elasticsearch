package com.example.elasticsearch.repository;

import com.example.elasticsearch.model.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findByName(String name);
    List<Employee> findBySalaryBetween(double min, double max); // Range query on the salary field

}
