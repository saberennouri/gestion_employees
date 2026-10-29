package tn.saber.GestionEmployes.Service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.saber.GestionEmployes.entity.AuditLog;
import tn.saber.GestionEmployes.repository.AuditLogRepository;

@Service
public class AuditLogService {
  private final AuditLogRepository auditLogRepository;
  public AuditLogService(AuditLogRepository auditLogRepository) {
    this.auditLogRepository = auditLogRepository;
  }
  @Transactional
  public void log(String action, String details) {
    Authentication authentication =
      SecurityContextHolder.getContext().getAuthentication();
    String actor ="ANONYMOUS";
    if(authentication!=null
      && authentication.isAuthenticated()
    && authentication.getName()!=null){
      actor=authentication.getName();
    }
    AuditLog auditLog = new AuditLog();
    auditLog.setUsername(actor);
    auditLog.setAction(action);
    auditLog.setDetails(details);
    auditLogRepository.save(auditLog);

  }
}
