package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.LoginDto;
import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestDto;
import hu.cubix.hr.zpalvolgyi.model.*;
import hu.cubix.hr.zpalvolgyi.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TimeOffRequestControllerIT {

    private static final String API_TIMEOFFR = "/api/timeoffrequests";

    private static final String API_LOGIN = "/api/login";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private FormService formService;

    @Autowired
    private PositionService positionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void testProcessFlow() {

        //ARRANGE
        String password = "pass";

        Position p1 = positionService.create(new Position("Accountant", EducationLevel.UNIVERSITY,500));
        Position p2 = positionService.create(new Position("Global Manager", EducationLevel.PHD,700));

        Employee e1 = new Employee("John", p1, 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0));
        e1.setUsername("user1");
        e1.setPassword(passwordEncoder.encode(password));

        Employee e2 = new Employee("Mary",p2, 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0));
        e2.setUsername("user2");
        e2.setPassword(passwordEncoder.encode(password));

        Form f1 = formService.create(new Form("Limited Partnership"));

        Company c1 = companyService.create(new Company(123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", new ArrayList<>(),f1));

        companyService.addNewEmployee(c1.getId(), e1);
        companyService.addNewEmployee(c1.getId(), e2);

        //ACT
        // create new time off request
        TimeOffRequestDto timeOffRequestDto = createTimeOffRequest(e1,e2,password);
        //ASSERT
        assertThat(timeOffRequestDto.getId()).isGreaterThan(0);

        //ACT
        //update endDate and set to status to Approved
        TimeOffRequestDto updatedTimeOffRequestDto = updateTimeOffRequest(timeOffRequestDto,e2,password);
        //ASSERT
        assertThat(updatedTimeOffRequestDto.getRequestStatus()).isEqualTo(RequestStatus.APPROVED);

        //ACT
        //try to delete it but it won't work because it is already approved
        deleteTimeOffRequest(updatedTimeOffRequestDto,e1,password);

    }

    private String getJwtToken(String username, String password){
        LoginDto loginDto = new LoginDto(username,password);

        return webTestClient
                .post()
                .uri(API_LOGIN)
                .bodyValue(loginDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    private TimeOffRequestDto createTimeOffRequest(Employee requester, Employee approver, String password){
        TimeOffRequestDto timeOffRequestDto = new TimeOffRequestDto(
                    LocalDateTime.of(2023, 11, 27, 0, 0, 0)
                    , LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                    ,requester.getName()
                    ,LocalDateTime.now()
                    ,approver.getName()
                    ,RequestStatus.WAITING_FOR_APPROVAL
        );

        String bearerToken = getJwtToken(requester.getUsername(), password);

        return webTestClient
                .post()
                .uri(API_TIMEOFFR)
                .bodyValue(timeOffRequestDto)
                .header("Authorization", "Bearer " + bearerToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeOffRequestDto.class)
                .returnResult()
                .getResponseBody();
    }

    private TimeOffRequestDto updateTimeOffRequest(TimeOffRequestDto timeOffRequestDto, Employee approver, String password){

        TimeOffRequestDto updatedTimeOffRequestDto = new TimeOffRequestDto(
                LocalDateTime.of(2023, 11, 27, 0, 0, 0)
                , LocalDateTime.of(2023, 12, 30, 0, 0, 0)
                ,timeOffRequestDto.getRequestedBy()
                ,LocalDateTime.now()
                ,timeOffRequestDto.getApprover()
                ,RequestStatus.APPROVED
        );

        String bearerToken = getJwtToken(approver.getUsername(), password);

        return webTestClient
                .put()
                .uri(API_TIMEOFFR + "/" + timeOffRequestDto.getId())
                .bodyValue(updatedTimeOffRequestDto)
                .header("Authorization", "Bearer " + bearerToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeOffRequestDto.class)
                .returnResult()
                .getResponseBody();
    }

    private void deleteTimeOffRequest(TimeOffRequestDto timeOffRequestDto, Employee requester, String password){

        String bearerToken = getJwtToken(requester.getUsername(), password);

        webTestClient
                .delete()
                .uri(API_TIMEOFFR + "/" + timeOffRequestDto.getId())
                .header("Authorization", "Bearer " + bearerToken)
                .exchange()
                .expectStatus().isForbidden();
    }
}
