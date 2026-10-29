package tn.saber.GestionEmployes.repository;

import tn.saber.GestionEmployes.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuditLogRepository
extends JpaRepository<AuditLog,Long> {
  Page<AuditLog> findByUsernameContainingIgnoreCase(
    String username, Pageable pageable);
  Page<AuditLog> findByActionIgnoreCase(
    String action, Pageable pageable
  );

 }
