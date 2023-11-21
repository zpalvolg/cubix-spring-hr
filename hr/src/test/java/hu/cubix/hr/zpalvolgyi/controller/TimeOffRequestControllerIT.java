package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestDto;
import hu.cubix.hr.zpalvolgyi.model.RequestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TimeOffRequestControllerIT {

    private static final String API_TIMEOFFR = "/api/timeoffrequests";

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testProcessFlow() {
        // create new time off request
        TimeOffRequestDto timeOffRequestDto = createTimeOffRequest();
        assertThat(timeOffRequestDto.getId()).isGreaterThan(0);

        //update endDate and set to status to Approved
        TimeOffRequestDto updatedTimeOffRequestDto = updateTimeOffRequest(timeOffRequestDto);
        assertThat(updatedTimeOffRequestDto.getRequestStatus()).isEqualTo(RequestStatus.APPROVED);

        //try to delete it but it won't work because it is already approved
        deleteTimeOffRequest(updatedTimeOffRequestDto);
    }

    private TimeOffRequestDto createTimeOffRequest(){
        TimeOffRequestDto timeOffRequestDto = new TimeOffRequestDto(
                    LocalDateTime.of(2023, 11, 27, 0, 0, 0)
                    , LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                    ,"Requester"
                    ,LocalDateTime.now()
                    ,"Approver"
                    ,RequestStatus.WAITING_FOR_APPROVAL
        );

        return webTestClient
                .post()
                .uri(API_TIMEOFFR)
                .bodyValue(timeOffRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeOffRequestDto.class)
                .returnResult()
                .getResponseBody();
    }

    private TimeOffRequestDto updateTimeOffRequest(TimeOffRequestDto timeOffRequestDto){

        TimeOffRequestDto updatedTimeOffRequestDto = new TimeOffRequestDto(
                LocalDateTime.of(2023, 11, 27, 0, 0, 0)
                , LocalDateTime.of(2023, 12, 30, 0, 0, 0)
                ,"Requester"
                ,LocalDateTime.now()
                ,"Approver"
                ,RequestStatus.APPROVED
        );


        return webTestClient
                .put()
                .uri(API_TIMEOFFR + "/" + timeOffRequestDto.getId())
                .bodyValue(updatedTimeOffRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TimeOffRequestDto.class)
                .returnResult()
                .getResponseBody();
    }

    private void deleteTimeOffRequest(TimeOffRequestDto timeOffRequestDto){
        webTestClient
                .delete()
                .uri(API_TIMEOFFR + "/" + timeOffRequestDto.getId())
                .exchange()
                .expectStatus().isBadRequest();
    }
}
