package hu.cubix.hr.zpalvolgyi;

import hu.cubix.hr.zpalvolgyi.mapper.EmployeeMapper;
import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.service.AbstractEmployeeService;
import hu.cubix.hr.zpalvolgyi.service.CompanyService;
import hu.cubix.hr.zpalvolgyi.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	private SalaryService salaryService;
	@Autowired
	private AbstractEmployeeService employeeService;
	@Autowired
	private CompanyService companyService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		printProcess(new Employee(1L, "John","Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
		printProcess(new Employee(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
		printProcess(new Employee(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
		printProcess(new Employee(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
		printProcess(new Employee(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));

		//generate sample data
		if(employeeService.findAll().isEmpty()) {
			employeeService.create(new Employee(1L, "John", "Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
			employeeService.create(new Employee(2L, "Mary","IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
			employeeService.create(new Employee(3L, "William","Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
			employeeService.create(new Employee(4L, "Jennifer","Sales Intern", 400, LocalDateTime.of(2022, 9, 3, 0, 0, 0)));
			employeeService.create(new Employee(5L, "Michael","Global Manager", 1500, LocalDateTime.of(2016, 5, 3, 0, 0, 0)));

			companyService.create(new Company(1L, 123L, "Test-1 Ldt.", "Debrecen, Piac Street 123", employeeService.findAll()));
		}
	}

	private void printProcess(Employee employee){
		System.out.println(employee.getId() + ". ----------------------------------------------------------------------------------------");
		System.out.println(employee.getName() + " - " + employee.getJob());
		System.out.println("Salary before raise: " + employee.getSalary());
		salaryService.setNewSalary(employee);
		System.out.println("Salary after raise: " + employee.getSalary());
		System.out.println("----------------------------------------------------------------------------------------");
	}
}
