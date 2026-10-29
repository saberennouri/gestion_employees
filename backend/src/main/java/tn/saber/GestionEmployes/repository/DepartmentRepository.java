package tn.saber.GestionEmployes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.saber.GestionEmployes.entity.Department;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

  Optional<Department> findByDepartmentNameIgnoreCase(String departmentName);

  boolean existsByDepartmentNameIgnoreCase(String departmentName);
}
