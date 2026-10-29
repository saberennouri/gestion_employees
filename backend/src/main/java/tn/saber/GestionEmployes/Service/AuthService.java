package tn.saber.GestionEmployes.Service;

import tn.saber.GestionEmployes.dto.AuthResponse;
import tn.saber.GestionEmployes.dto.LoginRequest;
import tn.saber.GestionEmployes.dto.RegisterRequest;
import tn.saber.GestionEmployes.entity.Permission;
import tn.saber.GestionEmployes.entity.User;
import tn.saber.GestionEmployes.repository.UserRepository;
import tn.saber.GestionEmployes.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;



@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public void register(RegisterRequest request) {

    if (userRepository
      .findByUsername(request.username())
      .isPresent()) {

      throw new RuntimeException(
        "Username already exists"
      );
    }

    User user = new User();

    user.setUsername(request.username());

    user.setPassword(
      passwordEncoder.encode(
        request.password()
      )
    );

    user.setRole("USER");

    userRepository.save(user);
  }

  public AuthResponse login(LoginRequest request) {

    User user = userRepository
      .findByUsername(request.username())
      .orElseThrow(() ->
        new RuntimeException("Invalid username or password")
      );

    if (!passwordEncoder.matches(
      request.password(),
      user.getPassword())) {
      throw new RuntimeException("Invalid username or password");
    }

    String token = jwtService.generateToken(
      user.getUsername(),
      user.getRole()
    );

    Set<String> permissions = user.getPermissions()
      .stream()
      .map(Permission::getName)
      .collect(java.util.stream.Collectors.toSet());

    return new AuthResponse(
      token,
      user.getUsername(),
      user.getRole(),
      permissions
    );
  }

}
