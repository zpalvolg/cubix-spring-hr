package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private List<EmployeeDto> employees = new ArrayList<>();

    {
        employees.add(new EmployeeDto(1L, "Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees.add(new EmployeeDto(2L, "IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
        employees.add(new EmployeeDto(3L, "Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees.add(new EmployeeDto(4L, "Sales Intern", 400, LocalDateTime.of(2023, 5, 3, 0, 0, 0)));
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employees;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
        EmployeeDto employeeDto = employees.stream().filter(employeeDto1 -> employeeDto1.getId().equals(id)).findAny().orElse(null);

        if(employeeDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto newEmployeeDto) {
        EmployeeDto employeeDto = employees.stream().filter(employeeDto1 -> employeeDto1.getId().equals(newEmployeeDto.getId())).findAny().orElse(null);

        if(employeeDto == null) {
            employees.add(newEmployeeDto);
            return ResponseEntity.ok(newEmployeeDto);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto updatedEmployeeDto) {
        updatedEmployeeDto.setId(id);

        EmployeeDto employeeDto = employees.stream().filter(employeeDto1 -> employeeDto1.getId().equals(updatedEmployeeDto.getId())).findAny().orElse(null);

        if(employeeDto == null) {
            return ResponseEntity.notFound().build();
        }else{
            long index = employees.indexOf(employeeDto);
            employees.set((int)index, updatedEmployeeDto);
            return ResponseEntity.ok(updatedEmployeeDto);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        EmployeeDto employeeDto = employees.stream().filter(employeeDto1 -> employeeDto1.getId().equals(id)).findAny().orElse(null);
        employees.remove(employeeDto);
    }

    @GetMapping("/salaryGreaterThan/{salaryLimit}")
    public List<EmployeeDto> findBySalary(@PathVariable int salaryLimit) {
        return employees.stream().filter(employeeDto1 -> employeeDto1.getSalary() > salaryLimit).collect(Collectors.toList());
    }
}
