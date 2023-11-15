package hu.cubix.hr.zpalvolgyi.mapper;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "form", source = "form.name")
    public CompanyDto companyToDto(Company company);

    @InheritInverseConfiguration
    public Company dtoToCompany(CompanyDto companyDto);

    public List<CompanyDto> companiesToDtos(List<Company> companies);

    public List<Company> dtosToCompanies(List<CompanyDto> companyDtos);

    @Mapping(target = "job", source = "position.name")
    public EmployeeDto employeeToDto(Employee employee);
    @InheritInverseConfiguration
    public Employee dtoToEmployee(EmployeeDto employeeDto);
    public List<EmployeeDto> employeesToDtos(List<Employee> employees);
    public List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);
}
