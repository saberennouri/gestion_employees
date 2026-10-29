
package tn.saber.GestionEmployes.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.saber.GestionEmployes.Service.UserService;
import tn.saber.GestionEmployes.dto.PermissionRequest;
import tn.saber.GestionEmployes.dto.UserResponse;
import tn.saber.GestionEmployes.entity.User;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  private UserResponse toResponse(User user) {
    Set<String> permissions = user.getPermissions()
      .stream()
      .map(permission -> permission.getName())
      .collect(java.util.stream.Collectors.toSet());

    return new UserResponse(
      user.getId(),
      user.getUsername(),
      user.getRole(),
      permissions
    );
  }

  @GetMapping
  public List<UserResponse> getAllUsers() {
    return userService.getAllUsers()
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(
    @PathVariable Long id) {

    return userService.getUserById(id)
      .map(this::toResponse)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {
    try {
      User created = userService.createUser(user);

      return ResponseEntity.status(HttpStatus.CREATED)
        .body(toResponse(created));

    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
    @PathVariable Long id,
    @RequestBody User userDetails) {
    try {
      User updated = userService.updateUser(id, userDetails);
      return ResponseEntity.ok(toResponse(updated));

    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}/permissions")
  public ResponseEntity<?> updatePermissions(
    @PathVariable Long id,
    @RequestBody PermissionRequest request) {
    try {
      User updated = userService.updateUserPermissions(
        id, request.permissionIds());

      return ResponseEntity.ok(toResponse(updated));

    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(
    @PathVariable Long id) {
    try {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();

    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
