package com.webApp.HRdatabase.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.webApp.HRdatabase.dto.DepartmentDto;
import com.webApp.HRdatabase.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double salary;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "departmentId")
    private Department department;

    public Employee(String firstName, String lastName, String email,
                    String phoneNumber, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
    }

    public Employee(String firstName, String lastName, String email,
                    String phoneNumber, double salary, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.department = department;
    }


}
