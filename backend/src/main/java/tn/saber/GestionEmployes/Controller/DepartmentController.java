package tn.saber.GestionEmployes.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.saber.GestionEmployes.Service.DepartmentService;
import tn.saber.GestionEmployes.dto.DepartmentRequest;
import tn.saber.GestionEmployes.dto.DepartmentResponse;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

  private final DepartmentService departmentService;

  @GetMapping
  public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

    return ResponseEntity.ok(
      departmentService.getAllDepartments()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<DepartmentResponse> getDepartmentById(
    @PathVariable Long id) {

    return ResponseEntity.ok(
      departmentService.getDepartmentById(id)
    );
  }

  @PostMapping
  public ResponseEntity<DepartmentResponse> addDepartment(
    @Valid @RequestBody DepartmentRequest request) {

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(departmentService.addDepartment(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<DepartmentResponse> updateDepartment(
    @PathVariable Long id,
    @Valid @RequestBody DepartmentRequest request) {

    return ResponseEntity.ok(
      departmentService.updateDepartment(id, request)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDepartment(
    @PathVariable Long id) {

    departmentService.deleteDepartment(id);

    return ResponseEntity.noContent().build();
  }
}
