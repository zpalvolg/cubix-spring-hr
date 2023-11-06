package hu.cubix.hr.zpalvolgyi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private EducationLevel qualification;
    private int min_salary;
    @OneToMany(mappedBy = "position")
    @JsonBackReference
    private Set<Employee> employees;
    public Position() {
    }

    public Position(String name, EducationLevel qualification, int min_salary) {
        this.name = name;
        this.qualification = qualification;
        this.min_salary = min_salary;
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

    public EducationLevel getQualification() {
        return qualification;
    }

    public void setQualification(EducationLevel qualification) {
        this.qualification = qualification;
    }

    public int getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(int min_salary) {
        this.min_salary = min_salary;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


}
