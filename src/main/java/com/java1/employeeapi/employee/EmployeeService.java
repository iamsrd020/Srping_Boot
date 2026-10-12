package com.java1.employeeapi.employee;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    public Employee create(EmployeeRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }
        return save(new Employee(request.name(), request.email(), request.salary(), request.departmentId()));
    }

    public Employee replace(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        if (repository.existsByEmailAndIdNot(request.email(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setSalary(request.salary());
        employee.setDepartmentId(request.departmentId());
        return save(employee);
    }

    public Employee updateSalary(Long id, SalaryPatchRequest request) {
        Employee employee = findById(id);
        employee.setSalary(request.salary());
        return save(employee);
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        repository.delete(employee);
    }

    private Employee save(Employee employee) {
        try {
            return repository.saveAndFlush(employee);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee violates a database constraint");
        }
    }
}
