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

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Company> findAll(Boolean full){
        List<Company> companiesWithoutEmployees = new ArrayList<>();
        List<Company> companies = companyRepository.findAll();

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
        Company company = companyRepository.findById(id).orElse(null);

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

    @Transactional
    public Company create(Company newCompany){
        return companyRepository.save(newCompany);
    }

    @Transactional
    public Company update(Company updatedCompany){
        if(findById(updatedCompany.getId(),null) == null) {
            return null;
        }
        return companyRepository.save(updatedCompany);
    }

    @Transactional
    public void delete(long id){
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addNewEmployee(long companyId, Employee employee){
        Company company = findById(companyId,true);

        if (company == null){
            return null;
        }
        else{
            employeeRepository.save(employee);
            company.getEmployees().add(employee);
            return companyRepository.save(company);
        }
    }

    @Transactional
    public void deleteEmployee(long companyId, long employeeId){
        Company company = company = findById(companyId,true);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        company.getEmployees().remove(employee);
        companyRepository.save(company);
    }

    @Transactional
    public Company updateEmployeeList(long companyId, List<Employee> employees){
        Company company = company = findById(companyId,true);

        if (company == null){
            return null;
        }
        else{
            company.setEmployees(employees);
            return companyRepository.save(company);
        }
    }

}
