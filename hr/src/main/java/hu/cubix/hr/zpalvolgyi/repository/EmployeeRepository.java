package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySalaryIsGreaterThanEqual(int limit);

    List<Employee> findByJobIs(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByHiringDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
