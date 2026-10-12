package com.java1.employeeapi.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByOrderByIdAsc();

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
