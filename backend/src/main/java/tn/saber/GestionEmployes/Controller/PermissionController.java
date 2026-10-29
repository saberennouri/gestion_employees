package tn.saber.GestionEmployes.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.saber.GestionEmployes.entity.Permission;
import tn.saber.GestionEmployes.repository.PermissionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin(origins = "http://localhost:4200")
public class PermissionController {

  private final PermissionRepository permissionRepository;

  public PermissionController(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }
  @GetMapping
  public List<Permission> getAllPermissions() {
    return this.permissionRepository.findAll();
  }
}
