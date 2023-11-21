package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TimeOffRequestSpecification {

    public static Specification<TimeOffRequest> checkRequestStatus(RequestStatus requestStatus){
        return (root, cq, cb) -> cb.equal(root.get(TimeOffRequest_.requestStatus), requestStatus);
    }

    public static Specification<TimeOffRequest> requesterNameStartsWith(String prefix){
        return (root, cq, cb) -> cb.like(cb.lower(root.get(TimeOffRequest_.requestedBy).get(Employee_.name)), prefix.toLowerCase() + "%");
    }

    public static Specification<TimeOffRequest> approverNameStartsWith(String prefix){
        return (root, cq, cb) -> cb.like(cb.lower(root.get(TimeOffRequest_.approver).get(Employee_.name)), prefix.toLowerCase() + "%");
    }

    public static Specification<TimeOffRequest> requestDateInRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, cq, cb) -> cb.between(root.get(TimeOffRequest_.requestDate), startDate, endDate);
    }

    public static Specification<TimeOffRequest> startDateInRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, cq, cb) -> cb.between(root.get(TimeOffRequest_.startDate), startDate, endDate);
    }

    public static Specification<TimeOffRequest> endDateInRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, cq, cb) -> cb.between(root.get(TimeOffRequest_.endDate), startDate, endDate);
    }
}
