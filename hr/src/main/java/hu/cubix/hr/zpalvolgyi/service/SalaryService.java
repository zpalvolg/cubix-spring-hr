package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {
    @Autowired
    private EmployeeService employeeService;

    public void setNewSalary(Employee employee){
        int currentSalary = employee.getSalary();
        int percentage = employeeService.getPayRaisePercent(employee);
        double factor = 1 + ((double)percentage / 100);
        employee.setSalary((int)(currentSalary * factor));
    }

}
