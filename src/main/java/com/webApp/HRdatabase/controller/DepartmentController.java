package com.webApp.HRdatabase.controller;

import com.webApp.HRdatabase.service.DepartmentService;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/hr/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final ValidationService validationService;

    @Autowired
    public DepartmentController(DepartmentService departmentService,
                                ValidationService validationService) {
        this.departmentService = departmentService;
        this.validationService = validationService;
    }

}
