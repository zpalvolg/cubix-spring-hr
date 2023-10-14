package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/percentage")
    public int getSalaryPercentage(@RequestBody Employee employee) {
        return employeeService.getPayRaisePercent(employee);
    }
}
