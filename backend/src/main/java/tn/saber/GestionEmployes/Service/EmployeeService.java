package tn.saber.GestionEmployes.Service;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.saber.GestionEmployes.dto.EmployeeRequest;
import tn.saber.GestionEmployes.dto.EmployeeResponse;
import tn.saber.GestionEmployes.entity.Employee;
import tn.saber.GestionEmployes.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeService {


  List<EmployeeResponse> findAll();

  EmployeeResponse findById(Long id);

  EmployeeResponse create(EmployeeRequest request);

  EmployeeResponse update(
    Long id,
    EmployeeRequest request);

  void delete(Long id);

  List<EmployeeResponse> searchByName(
    String name);

  List<EmployeeResponse> findByDepartment(
    String department);

  Page<EmployeeResponse> findAll(
    Pageable pageable);


  EmployeeResponse mapToResponse(Employee employee);
}
