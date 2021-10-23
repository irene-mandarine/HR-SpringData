package com.webApp.HRdatabase.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webApp.HRdatabase.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue
    private Long departmentId;

    private String name;
    private String location;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "department")
    @JsonIgnore
    private List<Employee> employeeList;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
}

