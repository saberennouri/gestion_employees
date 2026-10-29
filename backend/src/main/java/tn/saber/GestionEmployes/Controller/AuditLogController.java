
package tn.saber.GestionEmployes.Controller;

import tn.saber.GestionEmployes.entity.AuditLog;
import tn.saber.GestionEmployes.repository.AuditLogRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

  private final AuditLogRepository repository;

  public AuditLogController(AuditLogRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Page<AuditLog> getLogs(
    @RequestParam(required = false) String username,
    @RequestParam(required = false) String action,
    @PageableDefault(
      size = 10,
      sort = "createdAt",
      direction = Sort.Direction.DESC
    ) Pageable pageable) {

    if (username != null && !username.isBlank()) {
      return repository.findByUsernameContainingIgnoreCase(
        username.trim(), pageable);
    }

    if (action != null && !action.isBlank()) {
      return repository.findByActionIgnoreCase(
        action.trim(), pageable);
    }

    return repository.findAll(pageable);
  }
}
