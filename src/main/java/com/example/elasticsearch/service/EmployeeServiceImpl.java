package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Employee;
import com.example.elasticsearch.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(employeeRepository::delete);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public Page<Employee> paginatedEmployees(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return employeeRepository.findAll(pageRequest);
    }

    @Override
    public List<Employee> getEmployeeBySalaryRange(double min, double max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    @Override
    public List<Employee> getEmployeeByNameMatchQuery(String name) {
        return employeeRepository.findByNameMatchQuery(name);
    }

    @Override
    public List<Employee> searchEmployeeByNameAndSalaryRange(String name, double minSalary, double maxSalary) {
        return employeeRepository.findByMatchNameAndSalaryRange(name, minSalary, maxSalary);
    }

    @Override
    public List<Employee> searchEmployeeBySalaryRange(double minSalary, double maxSalary) {
        return employeeRepository.findBySalaryRange(minSalary, maxSalary);
    }

}
