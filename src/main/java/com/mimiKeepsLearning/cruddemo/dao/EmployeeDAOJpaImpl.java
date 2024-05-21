package com.mimiKeepsLearning.cruddemo.dao;

import com.mimiKeepsLearning.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{

    // define field for entityManger
    private EntityManager entityManager ;

    // set up constructor injection
    @Autowired
    public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
        entityManager =theEntityManager;
    }


    @Override
    public List<Employee> findAll() {

        // create a query
        TypedQuery<Employee> theQuery = entityManager.createQuery("from Employee ", Employee.class);
        // execute query and get result list
        List<Employee>employees= theQuery.getResultList();
        // return the results
        return employees;
    }

    @Override
    public Employee findById(int theId) {
        // get the employee
        Employee theEmployee= entityManager.find(Employee.class, theId);
        // return the employee
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        //save or update  employee depending on the  id , if 0 it will be saved or inserted otherwise it will be updated
        Employee dbEmployee = entityManager.merge(theEmployee);
        // return the dbEmployee
        return dbEmployee; // this one has its id updated in the db
    }

    @Override
    public void deleteById(int theId) {
        // find the employee by id
        Employee theEmployee  = entityManager.find(Employee.class, theId);
        // remove employee
        entityManager.remove(theEmployee);
    }
}
