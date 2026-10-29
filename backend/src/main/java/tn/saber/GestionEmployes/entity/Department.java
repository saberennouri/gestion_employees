package tn.saber.GestionEmployes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "departments")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "department_name", nullable = false, unique = true)
  private String departmentName;

  @Column(length = 500)
  private String description;

  public Department() {
  }

  public Department(Long id, String departmentName, String description) {
    this.id = id;
    this.departmentName = departmentName;
    this.description = description;
  }
}
