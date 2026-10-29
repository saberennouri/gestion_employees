package tn.saber.GestionEmployes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.saber.GestionEmployes.entity.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Optional<Permission> findByName(String name);
  boolean existsByName(String name);
}
