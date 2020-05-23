package com.example.cancel.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RobotRepository extends JpaRepository<Robot, Long> {

  @Query(
    value = "select 1,  Cast(pg_sleep(4) as varchar);", nativeQuery = true)
    List<Object> query();

}
