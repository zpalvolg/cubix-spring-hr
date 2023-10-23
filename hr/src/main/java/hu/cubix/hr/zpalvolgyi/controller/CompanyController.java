package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.mapper.CompanyMapper;
import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @GetMapping
    public List<CompanyDto> findAll(@RequestParam(name = "full", required = false) Boolean full) {
        List<Company> companies = companyService.findAll(full);
        return companyMapper.companiesToDtos(companies);
    }

    @GetMapping("/{id}")
    public CompanyDto findById(@PathVariable long id, @RequestParam(name = "full", required = false) Boolean full) {
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
    public CompanyDto addNewEmployee(@PathVariable long companyId, @RequestBody EmployeeDto employeeDto) {

        Company company = companyService.addNewEmployee(companyId,employeeDto);

        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            return companyMapper.companyToDto(company);
        }
    }

    @DeleteMapping("/{companyId}/employees/{EmployeeId}")
    public void deleteEmployee(@PathVariable long companyId, @PathVariable long EmployeeId) {
        companyService.deleteEmployee(companyId,EmployeeId);
    }


    @PutMapping("/{companyId}/employees")
    public CompanyDto updateEmployeeList(@PathVariable long companyId, @RequestBody List<EmployeeDto> employeeDtos) {
        Company company = companyService.updateEmployeeList(companyId,employeeDtos);

        if(company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            return companyMapper.companyToDto(company);
        }
    }
}
