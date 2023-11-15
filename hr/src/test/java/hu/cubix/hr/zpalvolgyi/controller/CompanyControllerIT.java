package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyControllerIT {

    private static final String API_COMPANIES = "/api/companies";
    
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testAddNewEmployee() {
        //ARRANGE
        // create a new company
        CompanyDto cDto1 = createCompany();

        //ACT
        //add a new employee to it
        addEmployee(cDto1.getId());

        //ASSERT
        //check if the number of employees is 1 for the given company after the adding
        CompanyDto cDto2 = findCompanyById(cDto1.getId());

        assertThat(cDto2.getEmployees().size()).isEqualTo(1);
    }

    @Test
    public void testDeleteEmployee() {
        //ARRANGE
        //create a company and add two employees to it
        CompanyDto cDto1 = createCompany();
        addEmployee(cDto1.getId());
        addEmployee(cDto1.getId());

        CompanyDto cDto2 = findCompanyById(cDto1.getId());

        //ACT
        //delete it the first employee of the company
        webTestClient
                .delete()
                .uri(API_COMPANIES + "/" + cDto2.getId() + "/employees/" + cDto2.getEmployees().get(0).getId())
                .exchange()
                .expectStatus().isOk();

        //ASSERT
        //check if the number of employees is 1 for the given company instead of 2 after the delete
        CompanyDto cDto3 = findCompanyById(cDto1.getId());

        assertThat(cDto3.getEmployees().size()).isEqualTo(1);
    }

    @Test
    public void testUpdateEmployeeList() {
        //ARRANGE
        //create a company and add two employees to it
        CompanyDto cDto1 = createCompany();
        addEmployee(cDto1.getId());
        addEmployee(cDto1.getId());

        //ACT
        //call update employee list rest api with a list of employee dtos request body
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        employeeDtos.add(new EmployeeDto("Will","Tester",500, LocalDateTime.of(2021, 4, 10, 0, 0, 0)));

        webTestClient
                .put()
                .uri(API_COMPANIES + "/" + cDto1.getId() + "/employees")
                .bodyValue(employeeDtos)
                .exchange()
                .expectStatus().isOk();

        //ASSERT
        //check if the number of employees is 1 for the given company after the update
        CompanyDto cDto2 = findCompanyById(cDto1.getId());

        assertThat(cDto2.getEmployees().size()).isEqualTo(1);
    }

    private CompanyDto createCompany(){
        return webTestClient
                .post()
                .uri(API_COMPANIES)
                .bodyValue(new CompanyDto(999L, "Test-4 Ldt.", "Debrecen, Petofi Street 678", "Corporation", new ArrayList<>()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CompanyDto.class)
                .returnResult()
                .getResponseBody();
    }

    private void addEmployee(long companyId){
        webTestClient
                .post()
                .uri(API_COMPANIES + "/" + companyId + "/employees")
                .bodyValue(new EmployeeDto("Joe","Tester",500, LocalDateTime.of(2021, 4, 10, 0, 0, 0)))
                .exchange()
                .expectStatus().isOk();
    }

    private CompanyDto findCompanyById(long id){
        return webTestClient
                .get()
                .uri(API_COMPANIES + "/" + id + "?full=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CompanyDto.class)
                .returnResult()
                .getResponseBody();
    }
}
