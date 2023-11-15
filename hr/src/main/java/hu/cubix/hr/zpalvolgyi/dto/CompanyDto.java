package hu.cubix.hr.zpalvolgyi.dto;

import java.util.List;

public class CompanyDto {
    private Long id;
    private Long registrationNumber;
    private String name;
    private String address;
    private String form;
    private List<EmployeeDto> employees;
    public CompanyDto() {
    }

    public CompanyDto(Long id, Long registrationNumber, String name, String address, String form, List<EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.form = form;
        this.employees = employees;
    }

    public CompanyDto(Long registrationNumber, String name, String address, String form, List<EmployeeDto> employees) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.form = form;
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

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
}
