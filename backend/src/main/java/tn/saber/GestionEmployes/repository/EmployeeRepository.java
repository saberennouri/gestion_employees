package tn.saber.GestionEmployes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.saber.GestionEmployes.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findByNameContainingIgnoreCase(String name);

  List<Employee> findByDepartment_DepartmentNameIgnoreCase(
    String departmentName
  );
}
