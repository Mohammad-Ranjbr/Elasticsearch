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
    List<Employee> getEmployeeBySalaryRange(double min, double max);
    List<Employee> getEmployeeByNameMatchQuery(String name);
    List<Employee> searchEmployeeByNameAndSalaryRange(String name, double minSalary, double maxSalary);
    List<Employee> searchEmployeeBySalaryRange(double minSalary, double maxSalary);

}
