package hu.cubix.hr.zpalvolgyi.dto;

import hu.cubix.hr.zpalvolgyi.model.RequestStatus;
import java.time.LocalDateTime;

public class TimeOffRequestFilterDto {

    private LocalDateTime createDateTimeStart;
    private LocalDateTime createDateTimeEnd;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String requestedBy;
    private LocalDateTime requestDate;
    private String approver;
    private RequestStatus requestStatus;

    public TimeOffRequestFilterDto() {
    }

    public LocalDateTime getCreateDateTimeStart() {
        return createDateTimeStart;
    }

    public void setCreateDateTimeStart(LocalDateTime createDateTimeStart) {
        this.createDateTimeStart = createDateTimeStart;
    }

    public LocalDateTime getCreateDateTimeEnd() {
        return createDateTimeEnd;
    }

    public void setCreateDateTimeEnd(LocalDateTime createDateTimeEnd) {
        this.createDateTimeEnd = createDateTimeEnd;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
