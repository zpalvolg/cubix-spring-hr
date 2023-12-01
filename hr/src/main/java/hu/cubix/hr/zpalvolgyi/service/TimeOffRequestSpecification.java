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

    public static Specification<TimeOffRequest> createDateIsBetween(LocalDateTime createDateTimeStart,
                                                                    LocalDateTime createDateTimeEnd) {
        return (root, cq, cb) -> cb.between(root.get(TimeOffRequest_.requestDate), createDateTimeStart, createDateTimeEnd);
    }

    public static Specification<TimeOffRequest> isStartDateLessThan(LocalDateTime date) {
        return (root, cq, cb) -> cb.lessThan(root.get(TimeOffRequest_.startDate), date);
    }

    public static Specification<TimeOffRequest> isEndDateGreaterThan(LocalDateTime date) {
        return (root, cq, cb) -> cb.greaterThan(root.get(TimeOffRequest_.endDate), date);
    }
}
