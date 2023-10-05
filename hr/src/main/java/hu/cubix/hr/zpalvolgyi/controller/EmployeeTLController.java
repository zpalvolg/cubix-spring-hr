package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeTLController {
    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1L, "Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
        employees.add(new Employee(2L, "IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
        employees.add(new Employee(3L, "Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
        employees.add(new Employee(4L, "Sales Intern", 400, LocalDateTime.of(2023, 5, 3, 0, 0, 0)));
    }

    @GetMapping("/")
    public String home(Map<String,Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());
        return "index";
    }

    @PostMapping("/employee")
    public String createAirport(Employee employee) {
        employees.add(employee);
        return "redirect:/";
    }
}
