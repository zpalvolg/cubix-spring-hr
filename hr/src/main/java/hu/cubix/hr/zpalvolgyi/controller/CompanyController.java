package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private List<CompanyDto> companies = new ArrayList<>();
    private List<EmployeeDto> employees1 = new ArrayList<>();
    private List<EmployeeDto> employees2 = new ArrayList<>();

    {
        employees1.add(new EmployeeDto(1L, "John","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees1.add(new EmployeeDto(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));

        employees2.add(new EmployeeDto(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees2.add(new EmployeeDto(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
        employees2.add(new EmployeeDto(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));

        companies.add(new CompanyDto(1L, 123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", employees1));
        companies.add(new CompanyDto(2L, 456L, "Test-2 Ldt.", "Debrecen, Petofi Street 456", employees2));
    }

    @GetMapping

    public List<CompanyDto> findAll(@RequestParam(name = "full", required = false) Boolean full) {

        List<CompanyDto> companiesWithoutEmployees = new ArrayList<>();

        for(CompanyDto companyDto: companies){
            CompanyDto companyDtoWithoutEmloyee = new CompanyDto(companyDto.getId(),companyDto.getRegistrationNumber(),companyDto.getName(),companyDto.getAddress(),null);
            companiesWithoutEmployees.add(companyDtoWithoutEmloyee);
        }

        if(full == null){
            return companiesWithoutEmployees;
        } else if (!full) {
            return companiesWithoutEmployees;
        }else {
            return companies;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable long id, @RequestParam(name = "full", required = false) Boolean full) {
        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(id)).findAny().orElse(null);

        if(companyDto == null) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDtoWithoutEmloyee = new CompanyDto(companyDto.getId(),companyDto.getRegistrationNumber(),companyDto.getName(),companyDto.getAddress(),null);

        if(full == null){
            return ResponseEntity.ok(companyDtoWithoutEmloyee);
        } else if (!full) {
            return ResponseEntity.ok(companyDtoWithoutEmloyee);
        }else {
            return ResponseEntity.ok(companyDto);
        }

    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto newCompanyDto) {
        CompanyDto companyDtoDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(newCompanyDto.getId())).findAny().orElse(null);

        if(companyDtoDto == null) {
            companies.add(newCompanyDto);
            return ResponseEntity.ok(newCompanyDto);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto updatedCompanyDto) {
        updatedCompanyDto.setId(id);

        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(updatedCompanyDto.getId())).findAny().orElse(null);

        if(companyDto == null) {
            return ResponseEntity.notFound().build();
        }else{
            long index = companies.indexOf(companyDto);
            companies.set((int)index, updatedCompanyDto);
            return ResponseEntity.ok(updatedCompanyDto);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(id)).findAny().orElse(null);
        companies.remove(companyDto);
    }

    @PostMapping("/{companyId}/employees")
    public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long companyId, @RequestBody EmployeeDto employeeDto) {

        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(companyId)).findAny().orElse(null);

        if(companyDto == null) {
            return ResponseEntity.notFound().build();
        }else{
            List<EmployeeDto> employeeDtos = companyDto.getEmployees();

            employeeDtos.add(employeeDto);

            companyDto.setEmployees(employeeDtos);

            return ResponseEntity.ok(companyDto);
        }
    }

    @DeleteMapping("/{companyId}/employees/{EmployeeId}")
    public void deleteEmployee(@PathVariable long companyId, @PathVariable long EmployeeId) {

        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(companyId)).findAny().orElse(null);

        List<EmployeeDto> employeeDtos = companyDto.getEmployees();

        EmployeeDto employeeDto = employeeDtos.stream().filter(emp -> emp.getId().equals(EmployeeId)).findAny().orElse(null);

        employeeDtos.remove(employeeDto);

        companyDto.setEmployees(employeeDtos);
    }

    @PutMapping("/{companyId}/employees")
    public ResponseEntity<CompanyDto> updateEmployeeList(@PathVariable long companyId, @RequestBody List<EmployeeDto> employeeDtos) {

        CompanyDto companyDto = companies.stream().filter(companyDto1 -> companyDto1.getId().equals(companyId)).findAny().orElse(null);

        if(companyDto == null) {
            return ResponseEntity.notFound().build();
        }else{

            companyDto.setEmployees(employeeDtos);

            return ResponseEntity.ok(companyDto);
        }
    }




}
