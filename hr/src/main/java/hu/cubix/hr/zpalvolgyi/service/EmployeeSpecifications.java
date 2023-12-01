package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Employee_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(long id){
        return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> employeeNameStartsWith(String prefix){
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), prefix.toLowerCase() + "%");
    }

    public static Specification<Employee> hasPositionName (String positionName){
        return (root, cq, cb) -> cb.equal(root.get(Employee_.position.getName()), positionName);
    }

    public static Specification<Employee> hasSalary(int salary){
        return (root, cq, cb) -> cb.between(root.get(Employee_.salary), (int)(salary * 0.95) , (int)(salary* 1.05));
    }

    public static Specification<Employee> hasHiringDate(LocalDateTime entryDate) {
        LocalDateTime startOfDay = LocalDateTime.of(entryDate.toLocalDate(), LocalTime.of(0, 0));
        return (root, cq, cb) -> cb.between(root.get(Employee_.hiringDate), startOfDay, startOfDay.plusDays(1));
    }

    public static Specification<Employee> companyNameStartsWith(String prefix){
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company.getName())), prefix.toLowerCase() + "%");
    }
}
