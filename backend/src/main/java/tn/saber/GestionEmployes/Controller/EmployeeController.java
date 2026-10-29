package tn.saber.GestionEmployes.Controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.saber.GestionEmployes.Service.EmployeeService;
import tn.saber.GestionEmployes.dto.EmployeeRequest;
import tn.saber.GestionEmployes.dto.EmployeeResponse;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  // ==========================================
  // 1. GET ALL + PAGINATION
  // ==========================================

  @GetMapping
  public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(
    @PageableDefault(
      size = 5,
      sort = "name"
    )
    Pageable pageable) {

    return ResponseEntity.ok(
      service.findAll(pageable)
    );
  }

  // ==========================================
  // 2. SEARCH BY NAME
  // ==========================================

  @GetMapping("/search")
  public ResponseEntity<List<EmployeeResponse>> searchEmployees(
    @RequestParam String name) {

    return ResponseEntity.ok(
      service.searchByName(name)
    );
  }

  // ==========================================
  // 3. EMPLOYEES BY DEPARTMENT
  // ==========================================

  @GetMapping("/department/{department}")
  public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(
    @PathVariable String department) {

    return ResponseEntity.ok(
      service.findByDepartment(department)
    );
  }

  // ==========================================
  // 4. GET BY ID
  // ==========================================

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> getEmployeeById(
    @PathVariable Long id) {

    return ResponseEntity.ok(
      service.findById(id)
    );
  }

  // ==========================================
  // 5. CREATE
  // ==========================================

  @PostMapping
  public ResponseEntity<EmployeeResponse> createEmployee(
    @Valid @RequestBody EmployeeRequest request) {

    EmployeeResponse response =
      service.create(request);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(response);
  }

  // ==========================================
  // 6. UPDATE
  // ==========================================

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponse> updateEmployee(
    @PathVariable Long id,
    @Valid @RequestBody EmployeeRequest request) {

    return ResponseEntity.ok(
      service.update(id, request)
    );
  }

  // ==========================================
  // 7. DELETE
  // ==========================================

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(
    @PathVariable Long id) {

    service.delete(id);

    return ResponseEntity
      .noContent()
      .build();
  }
}
