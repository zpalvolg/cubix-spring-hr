package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestFilterDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.RequestStatus;
import hu.cubix.hr.zpalvolgyi.model.TimeOffRequest;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import hu.cubix.hr.zpalvolgyi.repository.TimeOffRequestRepository;
import hu.cubix.hr.zpalvolgyi.security.HrUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static hu.cubix.hr.zpalvolgyi.service.TimeOffRequestSpecification.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeOffRequestService {

    @Autowired
    TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public TimeOffRequest findById(long id){
        return timeOffRequestRepository.findById(id).orElse(null);
    }

    @Transactional
    public TimeOffRequest create(TimeOffRequest timeOffRequest){
        processEmployee(timeOffRequest,timeOffRequest.getRequestedBy().getName(),"Requester");
        processEmployee(timeOffRequest,timeOffRequest.getApprover().getName(),"Approver");
        return timeOffRequestRepository.save(timeOffRequest);
    }

    @Transactional
    public TimeOffRequest update(TimeOffRequest updatedTimeOffRequest){
        TimeOffRequest timeOffRequest = findById(updatedTimeOffRequest.getId());

        String currentUser = getCurrentEmployee().getName();

        //only manager can change request status
        if((!currentUser.equals(timeOffRequest.getApprover().getName()))  && (!updatedTimeOffRequest.getRequestStatus().equals(timeOffRequest.getRequestStatus())))
            return null;

        if(validateRequestStatus(timeOffRequest) == null || timeOffRequest == null) {
            return null;
        }

        processEmployee(updatedTimeOffRequest,updatedTimeOffRequest.getRequestedBy().getName(),"Requester");
        processEmployee(updatedTimeOffRequest,updatedTimeOffRequest.getApprover().getName(),"Approver");

        return timeOffRequestRepository.save(updatedTimeOffRequest);
    }

    private Employee getCurrentEmployee() {
        return ((HrUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployee();
    }

    public TimeOffRequest deleteWithValidation(long id){
        TimeOffRequest timeOffRequest = findById(id);

        //only requester can delete request
        if(!getCurrentEmployee().getName().equals(timeOffRequest.getRequestedBy().getName()))
            return null;

        if(validateRequestStatus(timeOffRequest) == null || timeOffRequest == null) {
            return null;
        }else {
            timeOffRequestRepository.deleteById(timeOffRequest.getId());
            return timeOffRequest;
        }
    }

    private TimeOffRequest validateRequestStatus(TimeOffRequest timeOffRequest){
        if(timeOffRequest.getRequestStatus().equals(RequestStatus.WAITING_FOR_APPROVAL)){
            return timeOffRequest;
        }else{
            return null;
        }
    }

    private void processEmployee(TimeOffRequest timeOffRequest, String name, String role) {
        List<Employee> employees = employeeRepository.findByName(name);
        Employee employee = null;
        if(employees.isEmpty()){
            employee = employeeRepository.save(new Employee(name));
        }else{
            employee = employees.get(0);
        }

        switch (role){
            case "Requester":
                timeOffRequest.setRequestedBy(employee);
                break;
            case "Approver":
                timeOffRequest.setApprover(employee);
                break;
            default: break;
        }
    }

    public void delete(long id){
        timeOffRequestRepository.deleteById(id);
    }

    public List<TimeOffRequest> findAll(){
        return timeOffRequestRepository.findAll();
    }

    public Page<TimeOffRequest> findAllPageable(Pageable pageable) {
        return timeOffRequestRepository.findAll(pageable);
    }

    public List<TimeOffRequest> findAll(TimeOffRequestFilterDto timeOffRequestFilterDto) {

        RequestStatus requestStatus = timeOffRequestFilterDto.getRequestStatus();
        String requesterName = timeOffRequestFilterDto.getRequestedBy();
        String approverName = timeOffRequestFilterDto.getApprover();
        LocalDateTime createDateTimeStart = timeOffRequestFilterDto.getCreateDateTimeStart();
        LocalDateTime createDateTimeEnd = timeOffRequestFilterDto.getCreateDateTimeEnd();
        LocalDateTime startOfHolidayRequest = timeOffRequestFilterDto.getStartDate();
        LocalDateTime endOfHolidayRequest = timeOffRequestFilterDto.getEndDate();

        Specification<TimeOffRequest> spec = Specification.where(null);

        if(requestStatus != null) {
            spec = spec.and(checkRequestStatus(requestStatus));
        }

        if(StringUtils.hasLength(requesterName)) {
            spec = spec.and(requesterNameStartsWith(requesterName));
        }

        if(StringUtils.hasLength(approverName)) {
            spec = spec.and(approverNameStartsWith(approverName));
        }

        if (createDateTimeStart != null && createDateTimeEnd != null)
            spec = spec.and(createDateIsBetween(createDateTimeStart, createDateTimeEnd));

        if (startOfHolidayRequest != null) {
            spec = spec.and(isEndDateGreaterThan(startOfHolidayRequest));
        }

        if (endOfHolidayRequest != null) {
            spec = spec.and(isStartDateLessThan(endOfHolidayRequest));
        }

        return timeOffRequestRepository.findAll(spec);
    }

}