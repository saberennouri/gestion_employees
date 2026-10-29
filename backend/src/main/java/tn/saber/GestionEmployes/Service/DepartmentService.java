package tn.saber.GestionEmployes.Service;

import tn.saber.GestionEmployes.dto.DepartmentRequest;
import tn.saber.GestionEmployes.dto.DepartmentResponse;

import java.util.List;


public interface DepartmentService {

  List<DepartmentResponse> getAllDepartments();
  DepartmentResponse getDepartmentById(Long id);
  DepartmentResponse addDepartment(DepartmentRequest request);
  DepartmentResponse updateDepartment(Long id,DepartmentRequest request);
  void deleteDepartment(Long id);

}
