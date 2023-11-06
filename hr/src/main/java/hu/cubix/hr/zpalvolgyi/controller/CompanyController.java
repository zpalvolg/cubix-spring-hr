package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.mapper.CompanyMapper;
import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @GetMapping
    public List<CompanyDto> findAll(@RequestParam Optional<Boolean> full) {
        List<Company> companies = companyService.findAll(full);
        return companyMapper.companiesToDtos(companies);
    }

    @GetMapping("/{id}")
    public CompanyDto findById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        Company company = companyService.findById(id,full);

        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyMapper.companyToDto(company);
    }

    @PostMapping
    public CompanyDto create(@RequestBody CompanyDto newCompanyDto) {
        Company company = companyMapper.dtoToCompany(newCompanyDto);
        Company savedCompany = companyService.create(company);

        if(savedCompany != null) {
            return companyMapper.companyToDto(company);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable long id, @RequestBody CompanyDto updatedCompanyDto) {
        updatedCompanyDto.setId(id);
        Company company = companyMapper.dtoToCompany(updatedCompanyDto);
        Company updatedCompany = companyService.update(company);

        if(updatedCompany == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            return companyMapper.companyToDto(updatedCompany);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @PostMapping("/{companyId}/employees")
    public CompanyDto addNewEmployee(@PathVariable long companyId, @RequestBody Employee employee) {

        Company company = companyService.addNewEmployee(companyId,employee);

        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            return companyMapper.companyToDto(company);
        }
    }

    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public void deleteEmployee(@PathVariable long companyId, @PathVariable long employeeId) {
        companyService.deleteEmployee(companyId,employeeId);
    }


    @PutMapping("/{companyId}/employees")
    public CompanyDto updateEmployeeList(@PathVariable long companyId, @RequestBody List<Employee> employees) {
        Company company = companyService.updateEmployeeList(companyId,employees);

        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            return companyMapper.companyToDto(company);
        }
    }

    @GetMapping("/salaryGreaterThan/{salaryLimit}")
    public List<CompanyDto> findBySalary(@PathVariable int salaryLimit) {
        List<Company> companies = companyService.findByEmployeeSalary(salaryLimit);
        return companyMapper.companiesToDtos(companies);
    }

    @GetMapping("/numberOfEmp/{headcount}")
    public List<CompanyDto> findByNumberOfEmp(@PathVariable int headcount) {
        List<Company> companies = companyService.findByNumberOfEmp(headcount);
        return companyMapper.companiesToDtos(companies);
    }
}
