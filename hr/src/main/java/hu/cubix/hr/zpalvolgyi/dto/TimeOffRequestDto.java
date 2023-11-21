package hu.cubix.hr.zpalvolgyi.dto;

import hu.cubix.hr.zpalvolgyi.model.RequestStatus;

import java.time.LocalDateTime;

public class TimeOffRequestDto {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String requestedBy;
    private LocalDateTime requestDate;
    private String approver;
    private RequestStatus requestStatus;

    public TimeOffRequestDto() {
    }

    public TimeOffRequestDto(LocalDateTime startDate, LocalDateTime endDate, String requestedBy, LocalDateTime requestDate, String approver, RequestStatus requestStatus) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
        this.approver = approver;
        this.requestStatus = requestStatus;
    }

    public TimeOffRequestDto(Long id, LocalDateTime startDate, LocalDateTime endDate, String requestedBy, LocalDateTime requestDate, String approver, RequestStatus requestStatus) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
        this.approver = approver;
        this.requestStatus = requestStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
