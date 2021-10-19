package com.webApp.HRdatabase.service;

import com.webApp.HRdatabase.data.Department;
import com.webApp.HRdatabase.repository.DepartmentRepository;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ValidationService validationService;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             ValidationService validationService) {
        this.departmentRepository = departmentRepository;
        this.validationService = validationService;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        validationService.validateId(id);
        return departmentRepository.findById(id)
                .orElseThrow((() ->
                        new IllegalArgumentException("No such department.")));
    }

    public Department addDepartment(Department department) {
        validationService.validateDepartment(department);
        return departmentRepository.save(department);
    }

    public Department editDepartment(Department department, Long id) {
        getDepartment(id);
        validationService.validateDepartment(department);
        department.setDepartmentId(id);
        return departmentRepository.save(department);
    }
}
