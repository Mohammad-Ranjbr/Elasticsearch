package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    boolean deleteEmployee(Long id);
    Employee getEmployeeById(Long id);

}
