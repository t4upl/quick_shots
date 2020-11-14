package com.example.nplusone.repository;

import com.example.nplusone.model.Department;
import com.example.nplusone.model.Employee;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EmRepository {

  @PersistenceContext
  private EntityManager em;

  public String foo() {
    EntityGraph<?> graph = em.createEntityGraph("full-entity-graph");
    TypedQuery<Department> q = em.createQuery("SELECT a FROM Department a WHERE a.id = 1", Department.class);
    q.setHint("javax.persistence.fetchgraph", graph);
    System.out.println("QUERY");
    Department a = q.getSingleResult();
    System.out.println("EMPLOYESS");
    System.out.println(a.getEmployees().size());
    System.out.println(a.getEmployees().iterator().next().getName());
    System.out.println("TASKS");
    System.out.println(a.getEmployees().iterator().next().getTasks().size());

    return "foo";
  }

  @Transactional
  public String bar() {
    System.out.println("find department");
    Integer id  = 1;
    Department department = em.find(Department.class, id);
    System.out.println("find employee");
    Query query = em.createQuery("SELECT e FROM Employee e");
    List<Employee> resultList = query.getResultList();
    System.out.println("Post");
    Set<Employee> employees = department.getEmployees();
    System.out.println("Post2");
    System.out.println(employees.iterator().next().getName());

    Employee employee = new Employee();
    employee.setName("Karol");
    em.persist(employee);

    return "bar";

  }

}
