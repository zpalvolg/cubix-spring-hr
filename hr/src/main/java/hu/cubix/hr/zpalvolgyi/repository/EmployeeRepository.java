package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBySalaryIsGreaterThanEqual(int limit);

    @Query("SELECT e " +
            "FROM Employee e JOIN Position p " +
            "ON e.position.id = p.id " +
            "WHERE p.name = :job")
    List<Employee> findByJobIs(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByHiringDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p.name AS jobName, AVG(e.salary) AS avgSalary " +
            "FROM Employee e JOIN Position p " +
            "ON e.position.id = p.id " +
            "WHERE e.company.id = :companyId " +
            "GROUP BY jobName " +
            "ORDER BY avgSalary DESC")
    List<Object[]> findAverageSalariesByJobAndCompany(long companyId);
}

