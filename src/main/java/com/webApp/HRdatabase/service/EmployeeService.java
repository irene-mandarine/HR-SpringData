package com.webApp.HRdatabase.service;

import com.webApp.HRdatabase.exceptions.EmployeeNotFoundException;
import com.webApp.HRdatabase.data.Employee;
import com.webApp.HRdatabase.dto.EmployeeDto;
import com.webApp.HRdatabase.repository.EmployeeRepository;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertEmployeeToDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployee(Long id) {
        validationService.validateId(id);
        return employeeRepository.findById(id)
                .map(this::convertEmployeeToDto)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found."));
    }

    public EmployeeDto addEmployee(Employee employee) {
        validationService.validateEmployee(employee);
        checkEmail(employee);
        checkPhoneNumber(employee);
        Employee emp = employeeRepository.save(employee);
        return convertEmployeeToDto(emp);
    }

    public EmployeeDto editEmployee(Employee employee, Long id) {
        validationService.validateId(id);
        validationService.validateEmployee(employee);
        checkPhoneNumber(employee);
        employee.setId(id);
        employeeRepository.save(employee);
        return convertEmployeeToDto(employee);
    }

    private void checkPhoneNumber(Employee employee) {
        Optional<Employee> employeeByPhoneNumber = employeeRepository
                .findEmployeeByPhoneNumber(employee.getPhoneNumber());
        if (employeeByPhoneNumber.isPresent())
            throw new IllegalArgumentException("Such number already exists.");
    }

    private void checkEmail(Employee employee) {
        Optional<Employee> employeeByEmail = employeeRepository
                .findEmployeeByEmail(employee.getEmail());
        if (employeeByEmail.isPresent())
            throw new IllegalArgumentException("Such email already exists.");
    }

    private EmployeeDto convertEmployeeToDto(Employee employee) {
        return new EmployeeDto(employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDepartment());
    }
}
