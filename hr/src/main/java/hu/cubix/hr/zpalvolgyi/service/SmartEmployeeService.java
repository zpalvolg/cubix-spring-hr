package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.config.EmployeeConfigurationProperties;
import hu.cubix.hr.zpalvolgyi.config.EmployeeConfigurationProperties.Service.Smart;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SmartEmployeeService implements EmployeeService{

    @Autowired
    private EmployeeConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        System.out.println("SmartEmployeeService is active");

        //initialize values
        int resultPercentage = 0;

        double yearsSpentAtCompany = ChronoUnit.DAYS.between(employee.getHiringDate(), LocalDateTime.now()) / 365.0;

        System.out.println("Years spent at the company: " + yearsSpentAtCompany);

        //get confing related values
        Smart smartConfig = config.getService().getSmart();
        List<Double> getYearLimits = smartConfig.getYearLimits();
        List<Integer> raisePercentages = smartConfig.getRaisePercentages();

        for(int i = 0; i < getYearLimits.size(); i++){
            if(yearsSpentAtCompany >= getYearLimits.get(i)){
                System.out.println("Percentage found in the config: " + raisePercentages.get(i));
                resultPercentage = raisePercentages.get(i);
                break;
            }
        }

        return resultPercentage;
    }
}