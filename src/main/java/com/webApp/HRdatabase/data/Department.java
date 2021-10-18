package com.webApp.HRdatabase.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long departmentId;

    private String name;
    private String location;

    public Department(Long departmentId, String name, String location) {
        this.departmentId = departmentId;
        this.name = name;
        this.location = location;
    }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department() {
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", name='" + name +
                ", location=" + location +
                '}';
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
