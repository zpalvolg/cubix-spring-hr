package hu.cubix.hr.zpalvolgyi.service;


import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.repository.CompanyRepository;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> findAll(Optional<Boolean> full){
        List<Company> companiesWithoutEmployees = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();

        if(full.orElse(false)) {
            return companies;
        }else {
            for(Company company: companies){
                Company companyWithoutEmployee = new Company(company.getId(),company.getRegistrationNumber(),company.getName(),company.getAddress(),null, company.getForm());
                companiesWithoutEmployees.add(companyWithoutEmployee);
            }

            return companiesWithoutEmployees;
        }
    }

    public Company findById(long id, Optional<Boolean> full) {
        Company company = companyRepository.findById(id).orElse(null);

        if(company == null) {
            return null;
        }

        if(full.orElse(false)) {
            return company;
        }else {
            Company companyWithoutEmloyee = new Company(company.getId(),company.getRegistrationNumber(),company.getName(),company.getAddress(),null,company.getForm());

            return companyWithoutEmloyee;
        }
    }


    public Company create(Company newCompany){
        return companyRepository.save(newCompany);
    }


    public Company update(Company updatedCompany){
        if(!companyRepository.existsById(updatedCompany.getId())) {
            return null;
        }
        return companyRepository.save(updatedCompany);
    }

    public void delete(long id){
        companyRepository.deleteById(id);
    }


    public Company addNewEmployee(long companyId, Employee employee){
        Company company = companyRepository.findById(companyId).get();
        company.addEmployee(employee);
        employeeRepository.save(employee);
        return company;
    }

    public void deleteEmployee(long companyId, long employeeId){
        Company company = companyRepository.findById(companyId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setCompany(null);
        company.getEmployees().remove(employee);
        employeeRepository.save(employee);
    }


    public Company updateEmployeeList(long companyId, List<Employee> employees){
        Company company = companyRepository.findById(companyId).get();
        company.getEmployees().forEach(e -> e.setCompany(null));
        company.getEmployees().clear();
        employees.forEach(e -> {
            company.addEmployee(e);
            employeeRepository.save(e);
        });
        return company;
    }

    public List<Company> findByEmployeeSalary(int salary){
        return companyRepository.findByEmployeeSalary(salary);
    }
    public List<Company> findByNumberOfEmp(int headcount){
        return companyRepository.findByNumberOfEmp(headcount);
    }


}
