package tn.saber.GestionEmployes.dto;

import java.util.Set;

public record PermissionRequest(Set<Long> permissionIds) {
}
