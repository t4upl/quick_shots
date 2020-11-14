package com.example.nplusone.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;



@NamedEntityGraph(
  name = "full-entity-graph",
  attributeNodes = {
//    @NamedAttributeNode("name"),
//    @NamedAttributeNode(value = "employees"),
    @NamedAttributeNode(value = "employees", subgraph = "employees-subgraph"),
  },
  subgraphs = {
    @NamedSubgraph(
      name = "employees-subgraph",
      attributeNodes = {
//        @NamedAttributeNode("name"),
        @NamedAttributeNode("tasks"),
//        @NamedAttributeNode(value = "tasks", subgraph = "task-subgraph"),
      }
    ),
    @NamedSubgraph(
      name = "task-subgraph",
      attributeNodes = {
        @NamedAttributeNode("name")
      }
    )
  }
)
@NamedEntityGraph(
  name = "simple-entity-graph",
  attributeNodes = {
    @NamedAttributeNode("name")
  }
)
@Entity
@Table(name = "deparatment")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Getter
@ToString
public class Department {

  @Id
  private int id;

  private String name;

  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
//  @Fetch(FetchMode.JOIN)
//  @BatchSize(size = 2)
//  @JsonBackReference
  private Set<Employee> employees;

}
