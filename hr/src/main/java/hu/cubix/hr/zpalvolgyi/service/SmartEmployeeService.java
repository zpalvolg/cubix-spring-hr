package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.config.EmployeeConfigurationProperties;
import hu.cubix.hr.zpalvolgyi.config.EmployeeConfigurationProperties.Service.Smart;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class SmartEmployeeService implements EmployeeService{

    @Autowired
    private EmployeeConfigurationProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        System.out.println("SmartEmployeeService is active");

        //initialize values
        int resultPercentage = 0;

        double yearsSpentAtCompany = getWorkingPeriod(employee.getHiringDate());
        System.out.println("Years spent at the company: " + yearsSpentAtCompany);

        //get confing rated values
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

    private double getWorkingPeriod(LocalDateTime hiringDate){
        LocalDateTime now = LocalDateTime.now();

        Period period = Period.between(hiringDate.toLocalDate(), now.toLocalDate());

        return Double.parseDouble(period.getYears()+"."+period.getMonths());
    }
}
