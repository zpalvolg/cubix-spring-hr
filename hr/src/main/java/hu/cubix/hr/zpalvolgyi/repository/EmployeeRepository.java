package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.AverageSalaryByPosition;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> , JpaSpecificationExecutor<Employee> {
    List<Employee> findBySalaryIsGreaterThanEqual(int limit);

    @Query("SELECT e " +
            "FROM Employee e JOIN Position p " +
            "ON e.position.id = p.id " +
            "WHERE p.name = :job")
    List<Employee> findByJobIs(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByHiringDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    public List<Employee> findByName(String name);

    @Query("SELECT e.position.name as position, avg(e.salary) as averageSalary "
            + "FROM Company c "
            + "INNER JOIN c.employees e "
            + "WHERE c.id = :companyId "
            + "GROUP BY e.position.name "
            + "ORDER BY avg(e.salary) desc")
    List<AverageSalaryByPosition> findAverageSalariesByJobAndCompany(long companyId);

    @EntityGraph(attributePaths = {"manager", "managedEmployees"})
    Optional<Employee> findByUsername(String username);
}

