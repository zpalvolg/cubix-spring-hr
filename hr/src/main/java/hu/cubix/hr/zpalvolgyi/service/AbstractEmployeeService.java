package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.AverageSalaryByPosition;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.Position;
import hu.cubix.hr.zpalvolgyi.repository.EmployeeRepository;
import hu.cubix.hr.zpalvolgyi.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import static hu.cubix.hr.zpalvolgyi.service.EmployeeSpecifications.*;

@Service
public abstract class AbstractEmployeeService implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Employee create(Employee employee){
        processPosition(employee);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Employee updatedEmployee){
        if(findById(updatedEmployee.getId()) == null) {
            return null;
        }
        processPosition(updatedEmployee);
        return employeeRepository.save(updatedEmployee);
    }

    private void processPosition(Employee employee) {
        String positionName = employee.getPosition().getName();
        if(positionName != null) {
            List<Position> positions = positionRepository.findByName(positionName);
            Position position = null;
            if(positions.isEmpty()) {
                position = positionRepository.save(new Position(positionName));
            } else {
                position = positions.get(0);
            }
            employee.setPosition(position);
        }
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

    public List<AverageSalaryByPosition> findAverageSalariesByJobAndCompany(long companyId){
        return employeeRepository.findAverageSalariesByJobAndCompany(companyId);
    }

    public Page<Employee> findAllPageable(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> findAllSpec(Employee employee){
        long id = employee.getId();
        String employeeName = employee.getName();
        String positionName = "";
        int salary = employee.getSalary();
        LocalDateTime hiringDate = employee.getHiringDate();
        String companyName = "";

        if(employee.getPosition() != null) {
            positionName = employee.getPosition().getName();
        }

        if(employee.getCompany() != null){
            companyName = employee.getCompany().getName();
        }

        Specification<Employee> spec = Specification.where(null);

        if(id>0){
            spec = spec.and(hasId(id));
        }

        if(StringUtils.hasLength(employeeName)) {
            spec = spec.and(employeeNameStartsWith(employeeName));
        }

        if(StringUtils.hasLength(positionName)) {
            spec = spec.and(hasPositionName(positionName));
        }

        if(salary>0){
            spec = spec.and(hasSalary(salary));
        }

        if(!hiringDate.equals(LocalDateTime.MIN)){
            spec = spec.and(hasHiringDate(hiringDate));
        }

        if(StringUtils.hasLength(companyName)) {
            spec = spec.and(companyNameStartsWith(companyName));
        }

        return employeeRepository.findAll(spec);
    }

}
