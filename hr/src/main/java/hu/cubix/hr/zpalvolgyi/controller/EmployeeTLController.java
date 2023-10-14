package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {
    private List<Employee> employees = new ArrayList<>();
    private long id;

    {
        employees.add(new Employee(1L, "John","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees.add(new Employee(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
        employees.add(new Employee(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees.add(new Employee(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
        employees.add(new Employee(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));
        id = employees.size();
    }

    @GetMapping("/")
    public String home(Map<String,Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "index";
    }

    @PostMapping("/employee")
    public String createAirport(Employee employee) {
        employee.setId(++id);
        employees.add(employee);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) {
        Employee employee = employees.stream().filter(employee1 -> employee1.getId().equals(id)).findAny().orElse(null);

        if (employee != null) {
            employees.remove(employee);
        }
        return "redirect:/";
    }

    @GetMapping("update/{id}")
    public String updateId(@PathVariable long id, Map<String,Object> model) {
        Employee employee = employees.stream().filter(employee1 -> employee1.getId().equals(id)).findAny().orElse(null);

        if (employee != null) {
            model.put("currentEmployee", employee);
            return "update";
        }else{
            return "redirect:/";
        }
    }

    @PostMapping("/update")
    public String update(Employee updatedEmployee) {

        Employee employee = employees.stream().filter(employee1 -> employee1.getId().equals(updatedEmployee.getId())).findAny().orElse(null);

        if(employee != null) {
            long index = employees.indexOf(employee);
            employees.set((int)index, updatedEmployee);
        }

        return "redirect:/";
    }
}
