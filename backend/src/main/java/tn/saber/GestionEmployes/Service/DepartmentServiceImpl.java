package tn.saber.GestionEmployes.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.saber.GestionEmployes.dto.DepartmentRequest;
import tn.saber.GestionEmployes.dto.DepartmentResponse;
import tn.saber.GestionEmployes.entity.Department;
import tn.saber.GestionEmployes.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;

  @Override
  public List<DepartmentResponse> getAllDepartments() {

    return departmentRepository.findAll()
      .stream()
      .map(this::mapToResponse)
      .toList();
  }

  @Override
  public DepartmentResponse getDepartmentById(Long id) {

    Department department = departmentRepository.findById(id)
      .orElseThrow(() ->
        new RuntimeException("Département introuvable avec l'id : " + id)
      );

    return mapToResponse(department);
  }

  @Override
  public DepartmentResponse addDepartment(DepartmentRequest request) {

    if (departmentRepository
      .existsByDepartmentNameIgnoreCase(request.getDepartmentName())) {

      throw new RuntimeException(
        "Un département avec ce nom existe déjà"
      );
    }

    Department department = new Department();

    department.setDepartmentName(request.getDepartmentName());
    department.setDescription(request.getDescription());

    Department savedDepartment =
      departmentRepository.save(department);

    return mapToResponse(savedDepartment);
  }



  @Override
  public DepartmentResponse updateDepartment(Long id,
    DepartmentRequest request) {

    Department department = departmentRepository.findById(getDepartmentById(id).getId())
      .orElseThrow(() ->
        new RuntimeException(
          "Département introuvable avec l'id : " + id
        )
      );

    if (departmentRepository
      .findByDepartmentNameIgnoreCase(request.getDepartmentName())
      .filter(existing -> !existing.getId().equals(id))
      .isPresent()) {

      throw new RuntimeException(
        "Un autre département possède déjà ce nom"
      );
    }

    department.setDepartmentName(request.getDepartmentName());
    department.setDescription(request.getDescription());

    Department updatedDepartment =
      departmentRepository.save(department);

    return mapToResponse(updatedDepartment);
  }

  @Override
  public void deleteDepartment(Long id) {

    Department department = departmentRepository.findById(id)
      .orElseThrow(() ->
        new RuntimeException(
          "Département introuvable avec l'id : " + id
        )
      );

    departmentRepository.delete(department);
  }

  private DepartmentResponse mapToResponse(Department department) {

    return new DepartmentResponse(
      department.getId(),
      department.getDepartmentName(),
      department.getDescription()
    );
  }
}
