package hu.cubix.hr.zpalvolgyi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;

import java.util.List;

public class Company {
    private Long id;
    private Long registrationNumber;
    private String name;
    private String address;
    private List<EmployeeDto> employees;

    public Company() {
    }
    public Company(Long id, Long registrationNumber, String name, String address, List<EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
