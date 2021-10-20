package com.webApp.HRdatabase.service;

import com.webApp.HRdatabase.data.Employee;
import com.webApp.HRdatabase.repository.EmployeeRepository;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ValidationService validationService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           ValidationService validationService) {
        this.employeeRepository = employeeRepository;
        this.validationService = validationService;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id) {
        validationService.validateId(id);
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("No such Employee."));
    }

    public Employee addEmployee(Employee employee) {
        validationService.validateEmployee(employee);
        checkEmail(employee);
        checkPhoneNumber(employee);
        return employeeRepository.save(employee);
    }

    public Employee editEmployee(Employee employee, Long id) {
        validationService.validateId(id);
        validationService.validateEmployee(employee);
        checkPhoneNumber(employee);
        employee.setId(id);
        return employee;
    }

    private void checkPhoneNumber(Employee employee) {
        Optional<Employee> employeeByPhoneNumber = employeeRepository
                .findEmployeeByPhoneNumber(employee.getPhoneNumber());
        if (employeeByPhoneNumber.isPresent()) {
            throw new IllegalArgumentException("Such number already exists.");
        }
    }

    private void checkEmail(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepository
                .findEmployeeByEmail(employee.getEmail());
        if (employeeByEmail.isPresent()) {
            throw new IllegalArgumentException("Such email already exists.");
        }
    }
}
