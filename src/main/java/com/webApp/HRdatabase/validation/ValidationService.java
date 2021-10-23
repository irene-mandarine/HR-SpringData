package com.webApp.HRdatabase.validation;

import com.webApp.HRdatabase.data.Employee;
import com.webApp.HRdatabase.dto.DepartmentDto;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public ValidationService() {
    }

    public void validateId(Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Invalid ID.");
    }

    private void validateString(String s) {
        if (s == null || s.isEmpty() || s.trim().isEmpty())
            throw new IllegalArgumentException("Invalid data.");
    }

    private void validateDouble(double d) {
        if (d < 1)
            throw new IllegalArgumentException("Invalid data.");
    }

    public void validateDepartment(DepartmentDto department) {
        if (department == null)
            throw new IllegalArgumentException("Invalid Department");
        validateString(department.getName());
        validateString(department.getLocation());
    }

    public void validateEmployee(Employee employee) {
        if (employee == null)
            throw new IllegalArgumentException("Invalid Employee.");
        validateString(employee.getFirstName());
        validateString(employee.getLastName());
        validateString(employee.getEmail());
        validateString(employee.getPhoneNumber());
        validateDouble(employee.getSalary());
    }
}
