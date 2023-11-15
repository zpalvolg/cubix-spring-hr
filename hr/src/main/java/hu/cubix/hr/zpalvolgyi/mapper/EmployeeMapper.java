package hu.cubix.hr.zpalvolgyi.mapper;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "job", source = "position.name")
    public EmployeeDto employeeToDto(Employee employee);
    @InheritInverseConfiguration
    public Employee dtoToEmployee(EmployeeDto employeeDto);
    public List<EmployeeDto> employeesToDtos(List<Employee> employees);
    public List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);
}
