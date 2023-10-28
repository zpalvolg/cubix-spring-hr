package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.mapper.EmployeeMapper;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.service.AbstractEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private AbstractEmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeService.findAll();
        return employeeMapper.employeesToDtos(employees);
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);

        if(employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employeeMapper.employeeToDto(employee);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody @Valid EmployeeDto newEmployeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(newEmployeeDto);
        Employee savedEmployee = employeeService.create(employee);

        if(savedEmployee == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return employeeMapper.employeeToDto(savedEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody @Valid EmployeeDto updatedEmployeeDto) {
        updatedEmployeeDto.setId(id);

        Employee employee = employeeMapper.dtoToEmployee(updatedEmployeeDto);

        Employee updatedEmployee = employeeService.update(employee);

        if(updatedEmployee == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return employeeMapper.employeeToDto(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/salaryGreaterThan/{salaryLimit}")
    public List<EmployeeDto> findBySalary(@PathVariable int salaryLimit) {
        List<Employee> employees = employeeService.findBySalary(salaryLimit);
        return employeeMapper.employeesToDtos(employees);
    }

    @GetMapping("/job/{job}")
    public List<EmployeeDto> findByJob(@PathVariable String job) {
        List<Employee> employees = employeeService.findByJob(job);
        return employeeMapper.employeesToDtos(employees);
    }

    @GetMapping("/name/{name}")
    public List<EmployeeDto> findByName(@PathVariable String name) {
        List<Employee> employees = employeeService.findByName(name);
        return employeeMapper.employeesToDtos(employees);
    }

    @GetMapping("/hiringDate/{startDate}/{endDate}")
    public List<EmployeeDto> findByhiringDate(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        List<Employee> employees = employeeService.findByhiringDate(startDate,endDate);
        return employeeMapper.employeesToDtos(employees);
    }
}
