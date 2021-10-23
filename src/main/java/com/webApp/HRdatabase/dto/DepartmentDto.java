package com.webApp.HRdatabase.dto;

import com.webApp.HRdatabase.data.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    private String name;
    private String location;

}
