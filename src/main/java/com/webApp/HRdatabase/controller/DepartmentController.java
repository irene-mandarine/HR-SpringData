package com.webApp.HRdatabase.controller;

import com.webApp.HRdatabase.dto.DepartmentDto;
import com.webApp.HRdatabase.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/hr/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentDto> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("{id}")
    public DepartmentDto getDepartment(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @PostMapping
    public DepartmentDto addDepartment(@RequestBody DepartmentDto department) {
        return departmentService.addDepartment(department);
    }

    @PutMapping("{id}")
    public DepartmentDto editDepartment(@RequestBody DepartmentDto department,
                                     @PathVariable Long id) {
        return departmentService.editDepartment(department, id);
    }
}
