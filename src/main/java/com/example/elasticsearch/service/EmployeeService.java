package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);
    Iterable<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
    List<Employee> getEmployeeByName(String name);
    Page<Employee> paginatedEmployees(int page, int size);
    List<Employee> getEmployeeBySalaryBetween(double min, double max);

}
