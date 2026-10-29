package tn.saber.GestionEmployes.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tn.saber.GestionEmployes.dto.EmployeeRequest;
import tn.saber.GestionEmployes.dto.EmployeeResponse;
import tn.saber.GestionEmployes.entity.Department;
import tn.saber.GestionEmployes.entity.Employee;
import tn.saber.GestionEmployes.exception.EmployeeNotFoundException;
import tn.saber.GestionEmployes.repository.DepartmentRepository;
import tn.saber.GestionEmployes.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository repository;
  private final DepartmentRepository departmentRepository;

  public EmployeeServiceImpl(
    EmployeeRepository repository,
    DepartmentRepository departmentRepository
  ) {
    this.repository = repository;
    this.departmentRepository = departmentRepository;
  }

  // ==========================================
  // Entity → Response DTO
  // ==========================================

  private EmployeeResponse toResponse(Employee employee) {

    return new EmployeeResponse(
      employee.getId(),
      employee.getName(),
      employee.getEmail(),
      employee.getDepartment() != null
        ? employee.getDepartment().getId()
        : null,
      employee.getDepartment() != null
        ? employee.getDepartment().getDepartmentName()
        : "",
      employee.getSalary()
    );
  }

  // ==========================================
  // Request DTO → Entity
  // ==========================================

  private Employee toEntity(EmployeeRequest request) {

    Department department =
      departmentRepository
        .findById(request.getDepartmentId())
        .orElseThrow(() ->
          new RuntimeException(
            "Département introuvable : "
              + request.getDepartmentId()
          )
        );

    Employee employee = new Employee();

    employee.setName(request.getName());
    employee.setEmail(request.getEmail());
    employee.setDepartment(department);
    employee.setSalary(request.getSalary());

    return employee;
  }

  // ==========================================
  // GET ALL
  // ==========================================

  @Override
  public List<EmployeeResponse> findAll() {

    return repository.findAll()
      .stream()
      .map(this::toResponse)
      .toList();
  }



  // ==========================================
  // GET BY ID
  // ==========================================

  @Override
  public EmployeeResponse findById(Long id) {

    Employee employee =
      repository.findById(id)
        .orElseThrow(
          () -> new EmployeeNotFoundException(id)
        );

    return toResponse(employee);
  }

  // ==========================================
  // CREATE
  // ==========================================

  @Override
  public EmployeeResponse create(EmployeeRequest request) {

    Employee employee = toEntity(request);

    Employee saved = repository.save(employee);

    return toResponse(saved);
  }

  // ==========================================
  // UPDATE
  // ==========================================

  @Override
  public EmployeeResponse update(
    Long id,
    EmployeeRequest request
  ) {

    Employee employee =
      repository.findById(id)
        .orElseThrow(
          () -> new EmployeeNotFoundException(id)
        );

    // Récupérer le nouveau département
    Department department =
      departmentRepository
        .findById(request.getDepartmentId())
        .orElseThrow(() ->
          new RuntimeException(
            "Département introuvable : "
              + request.getDepartmentId()
          )
        );

    employee.setName(request.getName());
    employee.setEmail(request.getEmail());
    employee.setDepartment(department);
    employee.setSalary(request.getSalary());

    Employee updated = repository.save(employee);

    return toResponse(updated);
  }

  // ==========================================
  // DELETE
  // ==========================================

  @Override
  public void delete(Long id) {

    Employee employee =
      repository.findById(id)
        .orElseThrow(
          () -> new EmployeeNotFoundException(id)
        );

    repository.delete(employee);
  }

  // ==========================================
  // SEARCH BY NAME
  // ==========================================

  @Override
  public List<EmployeeResponse> searchByName(String name) {

    return repository
      .findByNameContainingIgnoreCase(name)
      .stream()
      .map(this::toResponse)
      .toList();
  }

  // ==========================================
  // SEARCH BY DEPARTMENT
  // ==========================================

  @Override
  public List<EmployeeResponse> findByDepartment(
    String department
  ) {

    return repository
      .findByDepartment_DepartmentNameIgnoreCase(department)
      .stream()
      .map(this::toResponse)
      .toList();
  }

  // ==========================================
  // PAGINATION + SORT
  // ==========================================

  @Override
  public Page<EmployeeResponse> findAll(
    Pageable pageable
  ) {

    return repository
      .findAll(pageable)
      .map(this::mapToResponse);
  }

  @Override
  public EmployeeResponse mapToResponse(Employee employee) {

    EmployeeResponse response = new EmployeeResponse();

    response.setId(employee.getId());
    response.setName(employee.getName());
    response.setEmail(employee.getEmail());
    response.setSalary(employee.getSalary());

    if (employee.getDepartment() != null) {

      response.setDepartmentId(
        employee.getDepartment().getId()
      );

      response.setDepartmentName(
        employee.getDepartment().getDepartmentName()
      );
    }

    return response;
  }



}
