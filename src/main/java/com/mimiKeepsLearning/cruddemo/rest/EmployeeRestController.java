package com.mimiKeepsLearning.cruddemo.rest;


import com.mimiKeepsLearning.cruddemo.entity.Employee;
import com.mimiKeepsLearning.cruddemo.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // expose "/employee" and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }
    // getting an employee by its id
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId ){
        Employee theEmployee = employeeService.findById(employeeId);
        if(theEmployee == null){
            throw new RuntimeException("Employee id not found - "+employeeId);
        }
        return theEmployee ;
    }
    // add mapping for POST /employees -add new employees
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
    // also just in case they pass an id in JSON ... set id to 0
    // this is to force a save of new item... instead of update
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee ;
    }
    // add mapping for PUT update /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }
    // add mapping for DELETE update /employees{employeeId} - delete existing employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId)
    {
        Employee tempEmployee = employeeService.findById(employeeId);
        // throw an exception if null
        if (tempEmployee == null){
            throw new RuntimeException("Employee is not found - "+employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Deleted employee id - "+employeeId ;
    }
}
