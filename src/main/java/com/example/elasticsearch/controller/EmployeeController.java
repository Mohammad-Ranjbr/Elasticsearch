package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.Employee;
import com.example.elasticsearch.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {
        Iterable<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if(employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        if(updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeeByName(@RequestParam("keyword") String name) {
        List<Employee> employees = employeeService.getEmployeeByName(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Employee>> paginatedEmployees(@RequestParam("page") int page,@RequestParam("size") int size) {
        Page<Employee> employees = employeeService.paginatedEmployees(page, size);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> filterEmployeesBySalaryRange(@RequestParam("min") double min,@RequestParam("max") double max) {
        List<Employee> employees = employeeService.getEmployeeBySalaryRange(min, max);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/match")
    public ResponseEntity<List<Employee>> searchByNameMatchQuery(@RequestParam("keyword") String name) {
        List<Employee> employees = employeeService.getEmployeeByNameMatchQuery(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/match-range")
    public ResponseEntity<List<Employee>> searchEmployeeByNameAndSalaryRange(
            @RequestParam("name") String name, @RequestParam("min") double min,@RequestParam("max") double max) {
        List<Employee> employees = employeeService.searchEmployeeByNameAndSalaryRange(name, min, max);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/range")
    public ResponseEntity<List<Employee>> searchEmployeeBySalaryRange(@RequestParam("min") double min,@RequestParam("max") double max) {
        List<Employee> employees = employeeService.searchEmployeeBySalaryRange(min, max);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
