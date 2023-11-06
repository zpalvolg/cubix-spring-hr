package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT DISTINCT c " +
            "FROM Company c JOIN Employee e " +
            "ON c.id = e.company.id " +
            "WHERE e.salary > :salary")
    List<Company> findByEmployeeSalary(int salary);

    @Query("SELECT c " +
            "FROM Company c " +
            "WHERE (SELECT COUNT(e) FROM c.employees e) > :headcount")
    List<Company> findByNumberOfEmp(int headcount);
}
