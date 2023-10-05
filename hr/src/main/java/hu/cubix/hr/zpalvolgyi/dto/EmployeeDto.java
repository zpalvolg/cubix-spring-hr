package hu.cubix.hr.zpalvolgyi.dto;

import java.time.LocalDateTime;

public class EmployeeDto {
    private Long id;
    private String job;
    private int salary;
    private LocalDateTime hiringDate;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String job, int salary, LocalDateTime hiringDate) {
        this.id = id;
        this.job = job;
        this.salary = salary;
        this.hiringDate = hiringDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDateTime getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDateTime hiringDate) {
        this.hiringDate = hiringDate;
    }
}
