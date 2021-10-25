package com.webApp.HRdatabase.dto;

import com.webApp.HRdatabase.data.Department;
import com.webApp.HRdatabase.data.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Department department;

}
