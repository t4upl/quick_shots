package com.example.nplusone.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

import com.example.nplusone.model.Department;
import com.example.nplusone.model.Employee;
import com.example.nplusone.repository.DepartmentRespository;
import com.example.nplusone.repository.EmRepository;
import com.example.nplusone.repository.EmployeeRespository;
import com.example.nplusone.service.DepartmentService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FooController {

  private final EmployeeRespository employeeRespository;
  private final DepartmentRespository departmentRespository;
  private final EmRepository emRepository;
  private final DepartmentService departmentService;


  @GetMapping("foo")
  public String foo() {
    return "foo";
  }

  @GetMapping("employee")
  public ResponseEntity<Employee> employee(){
    List<Employee> all = employeeRespository.findAll();
    return ok(all.get(0));
  }

  @GetMapping("employees")
  public ResponseEntity<List<Employee>> employees() {
    List<Employee> all = employeeRespository.findAll();
    return new ResponseEntity<>(all, OK);
  }

  @GetMapping("deparmtents")
  public ResponseEntity<List<Department>> departments() {
    List<Department> all = departmentRespository.findAll();
    return ok(all);
  }


  @GetMapping("bar")
  public String bar() {

    System.out.println("Repository method");
    List<Department> all = departmentRespository.findAll();

    System.out.println("Filter go");

    System.out.println(all.get(0).getName());
    System.out.println(all.get(0).getEmployees().size());



//      .sorted()
//      .peek(x ->
        System.out.println("Lubiec ciastka");
//      .forEach(System.out::println);


    return "bar";
  }

  @GetMapping("nativeQuery")
  public String nativeQuery() {
    List<Department> departments = departmentRespository.nativeQuery();
    return "nativeQuery";
  }

  @GetMapping("em")
  public String em() {
    String foo = emRepository.foo();
    return "em";
  }

  @GetMapping("lazy-em")
  public String lazyEm() {
    String bar = emRepository.bar();
    return "em";
  }

  @GetMapping("departmentService-all")
  public String departmentServiceAll() throws InterruptedException {
    String bar = departmentService.getAll();
    return "departmentService-all";
  }






}
