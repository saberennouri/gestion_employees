package tn.saber.GestionEmployes.Controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.saber.GestionEmployes.Service.AuthService;
import tn.saber.GestionEmployes.dto.AuthResponse;
import tn.saber.GestionEmployes.dto.LoginRequest;
import tn.saber.GestionEmployes.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:4200")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(
    @Valid @RequestBody RegisterRequest request) {

    authService.register(request);
  }

  @PostMapping("/login")
  public AuthResponse login(
    @Valid @RequestBody LoginRequest request) {

    return  authService.login(request);
  }
}
