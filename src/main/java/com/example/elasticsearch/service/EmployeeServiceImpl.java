package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Employee;
import com.example.elasticsearch.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            Employee oldEmployee = optionalEmployee.get();
            oldEmployee.setName(employee.getName());
            oldEmployee.setSalary(employee.getSalary());
            oldEmployee.setDepartment(employee.getDepartment());
            return employeeRepository.save(oldEmployee);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            employeeRepository.delete(optionalEmployee.get());
            return true;
        }
        return false;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

}
