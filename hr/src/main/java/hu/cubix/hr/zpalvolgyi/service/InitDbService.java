package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        Position p2 = positionService.create(new Position("Network Architect", EducationLevel.COLLEGE,600));
        Position p3 = positionService.create(new Position("Global Manager", EducationLevel.PHD,700));


        Employee e1 = new Employee("John", p1, 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0));
        Employee e2 = new Employee("Mary",p2, 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0));
        Employee e3 = new Employee("William",p3, 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0));
        Employee e4 = new Employee("Jennifer",p1, 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0));
        Employee e5 = new Employee("Michael",p2, 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0));
        Employee e6 = new Employee("Jack",p3, 800, LocalDateTime.of(2015, 5, 3, 0, 0, 0));
        Employee e7 = new Employee("Joe",p1, 600, LocalDateTime.of(2021, 6, 3, 1, 0, 0));

        Form f1 = formService.create(new Form("Limited Partnership"));
        Form f2 = formService.create(new Form("LLC"));
        Form f3 = formService.create(new Form("Corporation"));

        Company c1 = companyService.create(new Company(123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", new ArrayList<>(),f1));
        Company c2 = companyService.create(new Company(456L, "Test-2 Ldt.", "Debrecen, Piac Street 456", new ArrayList<>(),f2));
        Company c3 = companyService.create(new Company(789L, "Test-3 Ldt.", "Debrecen, Piac Street 789", new ArrayList<>(),f3));

        companyService.addNewEmployee(c1.getId(), e1);
        companyService.addNewEmployee(c1.getId(), e2);

        companyService.addNewEmployee(c2.getId(), e3);
        companyService.addNewEmployee(c2.getId(), e4);

        companyService.addNewEmployee(c3.getId(), e5);
        companyService.addNewEmployee(c3.getId(), e6);
        companyService.addNewEmployee(c3.getId(), e7);

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

        TimeOffRequest timeOffR2 = timeOffRequestService.create(
                new TimeOffRequest(
                        LocalDateTime.of(2023, 12, 27, 0, 0, 0) //starDate
                        ,LocalDateTime.of(2023, 12, 30, 0, 0, 0) //endDate
                        ,e3 //requestedBy
                        ,LocalDateTime.now() //requestDate
                        ,e4 //approver
                        ,RequestStatus.APPROVED //requestStatus
                )
        );

        TimeOffRequest timeOffR3 = timeOffRequestService.create(
                new TimeOffRequest(
                        LocalDateTime.of(2024, 1, 27, 0, 0, 0) //starDate
                        ,LocalDateTime.of(2024, 1, 30, 0, 0, 0) //endDate
                        ,e5 //requestedBy
                        ,LocalDateTime.now() //requestDate
                        ,e6 //approver
                        ,RequestStatus.REJECTED //requestStatus
                )
        );
    }
}
