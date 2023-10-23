package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Company;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    private List<Company> companies = new ArrayList<>();
    private List<EmployeeDto> employees1 = new ArrayList<>();
    private List<EmployeeDto> employees2 = new ArrayList<>();

    {
        employees1.add(new EmployeeDto(1L, "John","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees1.add(new EmployeeDto(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));

        employees2.add(new EmployeeDto(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees2.add(new EmployeeDto(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
        employees2.add(new EmployeeDto(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));

        companies.add(new Company(1L, 123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", employees1));
        companies.add(new Company(2L, 456L, "Test-2 Ldt.", "Debrecen, Petofi Street 456", employees2));
    }

    public List<Company> findAll(Boolean full){
        List<Company> companiesWithoutEmployees = new ArrayList<>();

        for(Company company: companies){
            Company companyWithoutEmployee = new Company(company.getId(),company.getRegistrationNumber(),company.getName(),company.getAddress(),null);
            companiesWithoutEmployees.add(companyWithoutEmployee);
        }

        if(full == null){
            return companiesWithoutEmployees;
        } else if (!full) {
            return companiesWithoutEmployees;
        }else {
            return companies;
        }
    }

    public Company findById(long id, Boolean full) {
        Company company = companies.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);

        if(company == null) {
            return null;
        }

        Company companyWithoutEmloyee = new Company(company.getId(),company.getRegistrationNumber(),company.getName(),company.getAddress(),null);

        if(full == null){
            return companyWithoutEmloyee;
        } else if (!full) {
            return companyWithoutEmloyee;
        }else {
            return company;
        }
    }

    public Company create(Company newCompany){
        Company companyAlreadyExists = companies.stream().filter(c -> c.getId().equals(newCompany.getId())).findAny().orElse(null);

        if(companyAlreadyExists == null) {
            companies.add(newCompany);
            return newCompany;
        }else{
            return null;
        }
    }

    public Company update(Company updatedCompany){
        Company searchForCompany = companies.stream().filter(c -> c.getId().equals(updatedCompany.getId())).findAny().orElse(null);

        if(searchForCompany != null) {
            int index = companies.indexOf(searchForCompany);
            companies.set(index, updatedCompany);
            return updatedCompany;
        }else{
            return null;
        }
    }

    public void delete(long id){
        Company company = companies.stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
        companies.remove(company);
    }

    public Company addNewEmployee(long companyId, EmployeeDto employeeDto){
        Company company = companies.stream().filter(c -> c.getId().equals(companyId)).findAny().orElse(null);

        if (company == null){
            return null;
        }
        else{
            List<EmployeeDto> employeeDtos = company.getEmployees();
            employeeDtos.add(employeeDto);
            company.setEmployees(employeeDtos);
            return company;
        }
    }

    public void deleteEmployee(long companyId, long EmployeeId){
        Company company = companies.stream().filter(c -> c.getId().equals(companyId)).findAny().orElse(null);
        List<EmployeeDto> employeeDtos = company.getEmployees();
        EmployeeDto employeeDto = employeeDtos.stream().filter(emp -> emp.getId().equals(EmployeeId)).findAny().orElse(null);
        employeeDtos.remove(employeeDto);
        company.setEmployees(employeeDtos);
    }

    public Company updateEmployeeList(long companyId, List<EmployeeDto> employeeDtos){
        Company company = companies.stream().filter(c -> c.getId().equals(companyId)).findAny().orElse(null);

        if (company == null){
            return null;
        }
        else{
            company.setEmployees(employeeDtos);
            return company;
        }
    }
}
