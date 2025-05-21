package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Employee;

public interface EmployeeService {

    Employee createEmployee(Employee employee);
    Iterable<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeById(Long id);

}
