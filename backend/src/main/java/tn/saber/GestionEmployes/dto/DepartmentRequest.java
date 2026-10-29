package tn.saber.GestionEmployes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {

  @NotBlank(message = "Le nom du département est obligatoire")
  @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
  private String departmentName;

  @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
  private String description;
}
