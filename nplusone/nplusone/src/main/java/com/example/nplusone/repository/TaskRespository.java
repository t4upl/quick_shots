package com.example.nplusone.repository;

import com.example.nplusone.model.Employee;
import com.example.nplusone.model.Task;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TaskRespository extends CrudRepository<Task, Integer> {

  List<Task> findAll();


}
