package hu.cubix.hr.zpalvolgyi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Position position;
    private int salary;
    private LocalDateTime hiringDate;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "requestedBy")
    private List<TimeOffRequest> requestedTimeOffRequests;
    @OneToMany(mappedBy = "approver")
    private List<TimeOffRequest> approvedTimeOffRequests;

    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> managedEmployees;
    private String username;
    private String password;

    public Employee() {
    }

    public Employee(Long id, String name, Position position, int salary, LocalDateTime hiringDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hiringDate = hiringDate;
    }

    public Employee(String name, Position position, int salary, LocalDateTime hiringDate) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hiringDate = hiringDate;
    }

    public Employee(String name) {
        this.name = name;
        this.hiringDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<TimeOffRequest> getRequestedTimeOffRequests() {
        return requestedTimeOffRequests;
    }

    public void setRequestedTimeOffRequests(List<TimeOffRequest> requestedTimeOffRequests) {
        this.requestedTimeOffRequests = requestedTimeOffRequests;
    }

    public List<TimeOffRequest> getApprovedTimeOffRequests() {
        return approvedTimeOffRequests;
    }

    public void setApprovedTimeOffRequests(List<TimeOffRequest> approvedTimeOffRequests) {
        this.approvedTimeOffRequests = approvedTimeOffRequests;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Employee> getManagedEmployees() {
        return managedEmployees;
    }

    public void setManagedEmployees(List<Employee> managedEmployees) {
        this.managedEmployees = managedEmployees;
    }
}
