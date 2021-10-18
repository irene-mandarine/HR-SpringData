package com.webApp.HRdatabase.controller;

import com.webApp.HRdatabase.service.EmployeeService;
import com.webApp.HRdatabase.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/hr/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ValidationService validationService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              ValidationService validationService) {
        this.employeeService = employeeService;
        this.validationService = validationService;
    }
}
