package hu.cubix.hr.zpalvolgyi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Form;

import java.util.List;

public class CompanyDto {
    private Long id;
    private Long registrationNumber;
    private String name;
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Employee> employees;
    private Form form;

    public CompanyDto() {
    }

    public CompanyDto(Long id, Long registrationNumber, String name, String address, List<Employee> employees, Form form) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
        this.form = form;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
