package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.AverageSalaryByPosition;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {
    public int getPayRaisePercent(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    public Employee create(Employee employee);

    public Employee update(Employee updatedEmployee);

    public void delete(long id);

    public List<Employee> findBySalary(int salaryLimit);

    public List<Employee> findByJob(String job);

    public List<Employee> findByName(String name);

    public List<Employee> findByhiringDate(LocalDateTime startDate, LocalDateTime endDate);

    public List<AverageSalaryByPosition> findAverageSalariesByJobAndCompany(long companyId);

    public Page<Employee> findAllPageable(Pageable pageable);

    public List<Employee> findAllSpec(Employee employee);
}
