
package tn.saber.GestionEmployes.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tn.saber.GestionEmployes.entity.Permission;
import tn.saber.GestionEmployes.entity.User;
import tn.saber.GestionEmployes.repository.PermissionRepository;
import tn.saber.GestionEmployes.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

  public final UserRepository userRepository;
  public final PasswordEncoder passwordEncoder;

  private final PermissionRepository permissionRepository;
  private final AuditLogService auditLogService;

  @Autowired
  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    PermissionRepository permissionRepository,
    AuditLogService auditLogService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.permissionRepository = permissionRepository;
    this.auditLogService = auditLogService;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Transactional
  public User createUser(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new RuntimeException("Username already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(user.getRole().toUpperCase());

    User savedUser = userRepository.save(user);

    auditLogService.log(
      "CREATE_USER",
      "Création de l'utilisateur : " + savedUser.getUsername()
    );

    return savedUser;
  }

  @Transactional
  public User updateUser(Long id, User userDetails) {
    User user = userRepository.findById(id)
      .orElseThrow(() ->
        new RuntimeException("User not found : " + id));

    String oldUsername = user.getUsername();
    String oldRole = user.getRole();

    user.setUsername(userDetails.getUsername());
    user.setRole(userDetails.getRole().toUpperCase());

    if (userDetails.getPassword() != null
      && !userDetails.getPassword().isEmpty()) {
      user.setPassword(
        passwordEncoder.encode(userDetails.getPassword())
      );
    }

    User updatedUser = userRepository.save(user);

    auditLogService.log(
      "UPDATE_USER",
      "Modification de l'utilisateur ID " + id
        + " (ancien nom : " + oldUsername
        + ", nouveau nom : " + updatedUser.getUsername()
        + ", ancien rôle : " + oldRole
        + ", nouveau rôle : " + updatedUser.getRole() + ")"
    );

    if (userDetails.getPassword() != null
      && !userDetails.getPassword().isEmpty()) {
      auditLogService.log(
        "CHANGE_PASSWORD",
        "Mot de passe modifié pour l'utilisateur ID " + id
      );
    }

    return updatedUser;
  }

  @Transactional
  public User updateUserPermissions(
    Long userId, Set<Long> permissionIds) {

    User user = userRepository.findById(userId)
      .orElseThrow(() ->
        new RuntimeException("User not found : " + userId));

    Set<Long> ids = permissionIds == null
      ? Set.of()
      : permissionIds;

    Set<Permission> permissions =
      new HashSet<>(permissionRepository.findAllById(ids));

    if (permissions.size() != ids.size()) {
      throw new RuntimeException("Permissions not found");
    }

    user.setPermissions(permissions);
    User updatedUser = userRepository.save(user);

    auditLogService.log(
      "UPDATE_PERMISSIONS",
      "Permissions modifiées pour l'utilisateur : "
        + updatedUser.getUsername()
        + " (nombre de permissions : " + permissions.size() + ")"
    );

    return updatedUser;
  }

  @Transactional
  public void deleteUserById(Long id) {
    User user = userRepository.findById(id)
      .orElseThrow(() ->
        new RuntimeException("User not found : " + id));

    String deletedUsername = user.getUsername();

    // Journaliser avant la suppression
    auditLogService.log(
      "DELETE_USER",
      "Suppression de l'utilisateur ID " + id
        + ", nom : " + deletedUsername
    );

    userRepository.delete(user);
  }
}
