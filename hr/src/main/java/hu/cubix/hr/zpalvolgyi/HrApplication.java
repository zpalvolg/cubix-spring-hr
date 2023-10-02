package hu.cubix.hr.zpalvolgyi;

import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	private SalaryService salaryService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		printProcess(new Employee(1L, "Accountant", 700, LocalDateTime.of(2020, 4, 10, 0, 0, 0)));
		printProcess(new Employee(2L, "IT Specialist", 950, LocalDateTime.of(2017, 5, 11, 0, 0, 0)));
		printProcess(new Employee(3L, "Network Architect", 1200, LocalDateTime.of(2007, 6, 12, 0, 0, 0)));
		printProcess(new Employee(4L, "Sales Intern", 400, LocalDateTime.of(2023, 7, 13, 0, 0, 0)));
	}

	private void printProcess(Employee employee){
		System.out.println(employee.getId() + ". ----------------------------------------------------------------------------------------");
		System.out.println(employee.getJob() + " salary before raise: " + employee.getSalary());
		salaryService.setNewSalary(employee);
		System.out.println(employee.getJob() + " salary after raise: " + employee.getSalary());
		System.out.println("----------------------------------------------------------------------------------------");
	}
}
