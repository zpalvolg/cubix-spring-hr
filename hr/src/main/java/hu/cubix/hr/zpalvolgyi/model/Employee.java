package hu.cubix.hr.zpalvolgyi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JsonManagedReference
    private Position position;
    private int salary;
    private LocalDateTime hiringDate;
    @ManyToOne
    @JsonBackReference
    private Company company;

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
}
