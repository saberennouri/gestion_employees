package tn.saber.GestionEmployes.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeResponse {

  private Long id;

  private String name;

  private String email;

  private Long departmentId;

  private String departmentName;

  private Double salary;

  public EmployeeResponse() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public EmployeeResponse(Long id, String name, String email, Long departmentId, String departmentName, Double salary) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.departmentId = departmentId;
    this.departmentName = departmentName;
    this.salary = salary;
  }
}
