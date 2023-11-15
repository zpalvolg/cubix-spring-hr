package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT DISTINCT c FROM Company c JOIN FETCH c.employees e WHERE e.salary > :minSalary")
    List<Company> findByEmployeeSalary(int minSalary);

    @Query("SELECT DISTINCT c " +
            "FROM Company c " +
            "JOIN FETCH c.employees e " +
            "WHERE SIZE(c.employees) > :headcount")
    List<Company> findByNumberOfEmp(int headcount);

    @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")
    List<Company> findAllWithEmployees();

    @Query("SELECT c from Company c LEFT JOIN FETCH c.employees WHERE c.id = :id")
    Company findCompanyWithEmployeesById(long id);
}
