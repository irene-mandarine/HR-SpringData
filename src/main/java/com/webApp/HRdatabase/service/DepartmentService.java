package com.webApp.HRdatabase.service;

import com.webApp.HRdatabase.exceptions.DepartmentNotFoundException;
import com.webApp.HRdatabase.data.Department;
import com.webApp.HRdatabase.dto.DepartmentDto;
import com.webApp.HRdatabase.repository.DepartmentRepository;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<DepartmentDto> getDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::convertDepartmentToDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartment(Long id) {
        validationService.validateId(id);
        return departmentRepository.findById(id)
                .map(this::convertDepartmentToDto)
                .orElseThrow((() ->
                        new DepartmentNotFoundException("No such department.")));
    }

    public DepartmentDto addDepartment(DepartmentDto department) {
        validationService.validateDepartment(department);
        return convertDepartmentToDto(departmentRepository
                .save(convertToDepartment(department)));
    }

    public DepartmentDto editDepartment(DepartmentDto department, Long id) {
        getDepartment(id);
        validationService.validateDepartment(department);
        Department d = convertToDepartment(department);
        d.setDepartmentId(id);
        return convertDepartmentToDto(departmentRepository.save(d));
    }

    private DepartmentDto convertDepartmentToDto(Department department) {
        return new DepartmentDto(department.getName(), department.getLocation());
    }

    private Department convertToDepartment(DepartmentDto departmentDto) {
        return new Department(departmentDto.getName(), departmentDto.getLocation());
    }
}
