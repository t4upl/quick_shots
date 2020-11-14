package com.example.nplusone.repository;

import com.example.nplusone.model.Employee;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRespository extends CrudRepository<Employee, Integer> {

  List<Employee> findAll();

}
