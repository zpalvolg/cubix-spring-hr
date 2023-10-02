package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.stereotype.Service;

public class DefaultEmployeeService implements EmployeeService{
    @Override
    public int getPayRaisePercent(Employee employee) {
        System.out.println("DefaultEmployeeService is active");

        //initialize values
        int defaultPercentage = 5;
        System.out.println("Default percentage: " + defaultPercentage);

        return defaultPercentage;
    }
}
