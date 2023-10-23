package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

    private static final String API_EMPLOYEES = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCreateEmployeeSuccess() {
        EmployeeDto newEmployeeDto = new EmployeeDto(6L,"Joe","Tester",500, LocalDateTime.of(2021, 4, 10, 0, 0, 0));

        webTestClient
                .post()
                .uri(API_EMPLOYEES)
                .bodyValue(newEmployeeDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateEmployeeBadRequest() {
        //id is minus
        EmployeeDto newEmployeeDto = new EmployeeDto(-7L,"Joe","Tester",500, LocalDateTime.of(2021, 4, 10, 0, 0, 0));

        webTestClient
                .post()
                .uri(API_EMPLOYEES)
                .bodyValue(newEmployeeDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateEmployeeSuccess() {
        EmployeeDto UpdatedEmployeeDto = new EmployeeDto(1L, "John - New","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0));

        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + UpdatedEmployeeDto.getId())
                .bodyValue(UpdatedEmployeeDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateEmployeeBadRequest() {
        //salary is minus
        EmployeeDto UpdatedEmployeeDto = new EmployeeDto(1L, "John - New","Accountant", -700, LocalDateTime.of(2020, 4, 10, 0, 0, 0));

        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + UpdatedEmployeeDto.getId())
                .bodyValue(UpdatedEmployeeDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

}
