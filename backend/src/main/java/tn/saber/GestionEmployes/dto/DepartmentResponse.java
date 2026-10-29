package tn.saber.GestionEmployes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentResponse {

  private Long id;
  private String departmentName;
  private String description;
}
