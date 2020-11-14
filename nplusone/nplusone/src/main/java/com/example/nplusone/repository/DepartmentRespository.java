package com.example.nplusone.repository;

import com.example.nplusone.model.Department;
import com.example.nplusone.model.Employee;
import java.util.List;
import java.util.Optional;
import javax.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface DepartmentRespository extends CrudRepository<Department, Integer> {

//  @Query("select distinct u from Department u join fetch u.employees")
//  @EntityGraph(value = "full-entity-graph")
  @Transactional(propagation = Propagation.MANDATORY)
  List<Department> findAll();

  Optional<Department> findById(Integer id);

  @Query(
    value = "select * from deparatment  "
      + "join employee on deparatment.id = employee.departament_id "
      + "join task on employee.id = task.employee_id;",
    nativeQuery = true)
  List<Department> nativeQuery();

}
