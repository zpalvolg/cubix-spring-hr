package hu.cubix.hr.zpalvolgyi.model;

import java.time.LocalDateTime;

public class Employee {
    private Long id;
    private String job;
    private int salary;
    private LocalDateTime hiringDate;

    public Employee() {
    }

    public Employee(Long id, String job, int salary, LocalDateTime hiringDate) {
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                ", hiringDate=" + hiringDate +
                '}';
    }
}
