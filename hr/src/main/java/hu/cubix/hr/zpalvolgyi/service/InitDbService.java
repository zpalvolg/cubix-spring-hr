package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class InitDbService {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FormService formService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void clearDb(){

        //delete TimeOffRequests
        timeOffRequestService.findAll().forEach(t -> timeOffRequestService.delete(t.getId()));

        //delete Employees
        employeeService.findAll().forEach(e -> employeeService.delete(e.getId()));

        //delete Companies
        companyService.findAll(Optional.of(false)).forEach(c -> companyService.delete(c.getId()));

        //delete Forms
        formService.findAll().forEach(f -> formService.delete(f.getId()));

        //delete Positions
        positionService.findAll().forEach(p -> positionService.delete(p.getId()));
    }

    @Transactional
    public void insertTestData(){

        Position p1 = positionService.create(new Position("Accountant", EducationLevel.UNIVERSITY,500));
        Position p2 = positionService.create(new Position("Global Manager", EducationLevel.PHD,700));

        Employee e1 = new Employee("John", p1, 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0));
        e1.setUsername("user1");
        e1.setPassword(passwordEncoder.encode("pass"));

        Employee e2 = new Employee("Mary",p2, 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0));
        e2.setUsername("user2");
        e2.setPassword(passwordEncoder.encode("pass"));

        Form f1 = formService.create(new Form("Limited Partnership"));

        Company c1 = companyService.create(new Company(123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", new ArrayList<>(),f1));

        companyService.addNewEmployee(c1.getId(), e1);
        companyService.addNewEmployee(c1.getId(), e2);

        TimeOffRequest timeOffR1 = timeOffRequestService.create(
                new TimeOffRequest(
                LocalDateTime.of(2023, 11, 27, 0, 0, 0) //starDate
                ,LocalDateTime.of(2023, 11, 30, 0, 0, 0) //endDate
                ,e1 //requestedBy
                ,LocalDateTime.now() //requestDate
                ,e2 //approver
                ,RequestStatus.WAITING_FOR_APPROVAL //requestStatus
            )
        );
    }
}
