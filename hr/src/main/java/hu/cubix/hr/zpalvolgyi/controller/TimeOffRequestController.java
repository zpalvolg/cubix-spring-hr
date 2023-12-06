package hu.cubix.hr.zpalvolgyi.controller;

import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestDto;
import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestFilterDto;
import hu.cubix.hr.zpalvolgyi.mapper.TimeOffRequestMapper;
import hu.cubix.hr.zpalvolgyi.model.TimeOffRequest;
import hu.cubix.hr.zpalvolgyi.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/timeoffrequests")
public class TimeOffRequestController {

    @Autowired
    TimeOffRequestService timeOffRequestService;

    @Autowired
    TimeOffRequestMapper timeOffRequestMapper;

    @PostMapping
    @PreAuthorize("#timeOffRequestDto.requestedBy == authentication.principal.employee.name")
    public TimeOffRequestDto create(@RequestBody TimeOffRequestDto timeOffRequestDto){
        TimeOffRequest timeOffRequest = timeOffRequestMapper.dtoTotimeOffRequest(timeOffRequestDto);
        TimeOffRequest savedTimeOffReqest = timeOffRequestService.create(timeOffRequest);
        return timeOffRequestMapper.timeOffRequestToDto(savedTimeOffReqest);
    }

    @PutMapping("/{id}")
    public TimeOffRequestDto update(@PathVariable long id,@RequestBody TimeOffRequestDto timeOffRequestDto){
        timeOffRequestDto.setId(id);
        TimeOffRequest timeOffRequest = timeOffRequestMapper.dtoTotimeOffRequest(timeOffRequestDto);
        TimeOffRequest updateTimeOffRequest = timeOffRequestService.update(timeOffRequest);

        if(updateTimeOffRequest==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return timeOffRequestMapper.timeOffRequestToDto(timeOffRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        if (timeOffRequestService.deleteWithValidation(id) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<TimeOffRequestDto> findAll(){
        List<TimeOffRequest> timeOffRequests = timeOffRequestService.findAll();
        return timeOffRequestMapper.timeOffRequestsToDtos(timeOffRequests);
    }

    @GetMapping("/pageable")
    public List<TimeOffRequestDto> findAllPageable(Pageable pageable) {
        List<TimeOffRequest> timeOffRequests = null;

        Page<TimeOffRequest> page = timeOffRequestService.findAllPageable(pageable);

        System.out.println(page.getTotalElements());
        System.out.println(page.isFirst());
        System.out.println(page.isLast());
        System.out.println(page.getNumberOfElements());

        timeOffRequests = page.getContent();

        return timeOffRequestMapper.timeOffRequestsToDtos(timeOffRequests);
    }

    @PostMapping("/spec")
    public List<TimeOffRequestDto> findAllSpec(@RequestBody TimeOffRequestFilterDto timeOffRequestFilterDto) {
        List<TimeOffRequest> timeOffRequests = timeOffRequestService.findAll(timeOffRequestFilterDto);
        return timeOffRequestMapper.timeOffRequestsToDtos(timeOffRequests);
    }

}
