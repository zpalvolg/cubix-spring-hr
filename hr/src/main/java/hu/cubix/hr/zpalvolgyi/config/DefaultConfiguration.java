package hu.cubix.hr.zpalvolgyi.config;

import hu.cubix.hr.zpalvolgyi.service.DefaultEmployeeService;
import hu.cubix.hr.zpalvolgyi.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {
    @Bean
    public EmployeeService employeeService(){
        return new DefaultEmployeeService();
    }
}
