package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Position;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class AbstractEmployeeService implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee create(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee update(Employee updatedEmployee){
        if(findById(updatedEmployee.getId()) == null) {
            return null;
        }
        return employeeRepository.save(updatedEmployee);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findBySalary(int salaryLimit) {
        return employeeRepository.findBySalaryIsGreaterThanEqual(salaryLimit);
    }

    public List<Employee> findByJob(String job) {
        return employeeRepository.findByJobIs(job);
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByNameStartingWithIgnoreCase(name);
    }

    public List<Employee> findByhiringDate(LocalDateTime startDate, LocalDateTime endDate) {
        return employeeRepository.findByHiringDateBetween(startDate,endDate);
    }

    public List<Object[]> findAverageSalariesByJobAndCompany(long companyId){
        return employeeRepository.findAverageSalariesByJobAndCompany(companyId);
    }

    public Page<Employee> findAllPageable(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

}
