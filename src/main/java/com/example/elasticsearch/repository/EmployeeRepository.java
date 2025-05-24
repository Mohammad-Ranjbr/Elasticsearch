package com.example.elasticsearch.repository;

import com.example.elasticsearch.model.Employee;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findByName(String name);
    List<Employee> findBySalaryBetween(double min, double max); // Range query on the salary field
    @Query(value = "{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}") // DSL (Domain Specific Language) query using the @Query annotation
    List<Employee> findByNameMatchQuery(String name);
    @Query(value = "{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}},{\"range\": {\"salary\": {\"gte\": ?1,\"lte\": ?2}}}]}}")
    List<Employee> findByMatchNameAndSalaryRange(String name, double minSalary, double maxSalary);

}
