package com.example.nplusone.service;

import com.example.nplusone.model.Department;
import com.example.nplusone.model.Employee;
import com.example.nplusone.model.Task;
import com.example.nplusone.repository.DepartmentRespository;
import com.example.nplusone.repository.EmployeeRespository;
import com.example.nplusone.repository.TaskRespository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

  private final DepartmentRespository departmentRespository;
  private final EmployeeRespository employeeRespository;
  private final TaskRespository taskRespository;

  public DepartmentService(
    DepartmentRespository departmentRespository,
    EmployeeRespository employeeRespository,
    TaskRespository taskRespository) {
    this.departmentRespository = departmentRespository;
    this.employeeRespository = employeeRespository;
    this.taskRespository = taskRespository;
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public String getAll() throws InterruptedException {
    System.out.println("departments ONE");
    List<Department> departments = departmentRespository.findAll();
    departments.forEach(System.out::println);

    System.out.println("\n\ndepartments TWO");
    List<Department> departments2 = departmentRespository.findAll();
//    System.out.println("ONE");
//    Optional<Department> byId = departmentRespository.findById(1);
//    System.out.println("TWO");
//    Optional<Department> byId2 = departmentRespository.findById(1);
    Thread.sleep(1000L);
    departments2.forEach(System.out::println);

    return "getAll";
  }

}
