package tn.saber.GestionEmployes.dto;

import java.util.Set;

public record AuthResponse(
  String token,
  String username,
  String role,
  Set<String> permissions
) {
}
