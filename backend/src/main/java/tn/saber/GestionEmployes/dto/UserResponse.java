package tn.saber.GestionEmployes.dto;

import java.util.Set;

public record UserResponse(
  Long id,
  String username,
  String role,
  Set<String> permissions

) {



}
