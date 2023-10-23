package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class AbstractEmployeeService implements EmployeeService{
    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1L, "John","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees.add(new Employee(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
        employees.add(new Employee(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees.add(new Employee(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
        employees.add(new Employee(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(long id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
    }

    public void delete(long id) {
        Employee employee = employees.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
        employees.remove(employee);
    }

    public List<Employee> findBySalary(int salaryLimit) {
        return employees.stream().filter(e -> e.getSalary() > salaryLimit).toList();
    }

    public Employee create(Employee employee){
        if(findById(employee.getId()) != null) {
            return null;
        }
        employees.add(employee);
        return employee;
    }

    public Employee update(Employee updatedEmployee){
        Employee employee = findById(updatedEmployee.getId());

        if(employee == null) {
            return null;
        }
        int index = employees.indexOf(employee);

        employees.set(index, updatedEmployee);

        return updatedEmployee;
    }
}
