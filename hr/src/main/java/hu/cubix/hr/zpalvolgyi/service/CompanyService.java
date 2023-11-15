package hu.cubix.hr.zpalvolgyi.service;


import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Form;
import hu.cubix.hr.zpalvolgyi.model.Position;
import hu.cubix.hr.zpalvolgyi.repository.CompanyRepository;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import hu.cubix.hr.zpalvolgyi.repository.FormRepository;
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

    @Autowired
    private FormRepository formRepository;

    public List<Company> findAll(Optional<Boolean> full){
        List<Company> companiesWithoutEmployees = new ArrayList<>();
        List<Company> companies = companyRepository.findAllWithEmployees();

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
        Company company = companyRepository.findCompanyWithEmployeesById(id);

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

    @Transactional
    public Company create(Company newCompany){
        processForm(newCompany);
        return companyRepository.save(newCompany);
    }


    @Transactional
    public Company update(Company updatedCompany){
        if(companyRepository.findCompanyWithEmployeesById(updatedCompany.getId()) == null) {
            return null;
        }
        processForm(updatedCompany);
        return companyRepository.save(updatedCompany);
    }

    private void processForm(Company company) {
        String formName = company.getForm().getName();
        if(formName != null) {
            List<Form> forms = formRepository.findByName(formName);
            Form form = null;
            if(forms.isEmpty()) {
                form = formRepository.save(new Form(formName));
            } else {
                form = forms.get(0);
            }
            company.setForm(form);
        }
    }

    @Transactional
    public void delete(long id){
        Company company = companyRepository.findCompanyWithEmployeesById(id);

        if(company != null){
            company.getEmployees().forEach(e -> {
                e.setCompany(null);
                employeeRepository.save(e);
            });
        }
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addNewEmployee(long companyId, Employee employee){
        Company company = companyRepository.findCompanyWithEmployeesById(companyId);
        company.addEmployee(employee);
        employeeRepository.save(employee);
        return company;
    }

    @Transactional
    public void deleteEmployee(long companyId, long employeeId){
        Company company = companyRepository.findCompanyWithEmployeesById(companyId);
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setCompany(null);
        company.getEmployees().remove(employee);
        employeeRepository.save(employee);
    }


    @Transactional
    public Company updateEmployeeList(long companyId, List<Employee> employees){
        Company company = companyRepository.findCompanyWithEmployeesById(companyId);
        company.getEmployees().forEach(e -> {
            e.setCompany(null);
            employeeRepository.save(e);
        });
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
