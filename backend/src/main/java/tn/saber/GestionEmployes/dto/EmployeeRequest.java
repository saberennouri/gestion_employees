package tn.saber.GestionEmployes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class EmployeeRequest {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotNull(message = "Department is required")
  @Positive(message = "Invalid department ID")
  private Long departmentId;

  @NotNull(message = "Salary is required")
  @Positive(message = "Salary must be positive")
  private Double salary;

  public EmployeeRequest() {
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }
}
