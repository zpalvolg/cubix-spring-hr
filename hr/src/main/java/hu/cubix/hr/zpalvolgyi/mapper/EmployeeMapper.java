package hu.cubix.hr.zpalvolgyi.mapper;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    public EmployeeDto employeeToDto(Employee employee);

    public List<EmployeeDto> employeesToDtos(List<Employee> employees);

    public Employee dtoToEmployee(EmployeeDto employeeDto);
}
