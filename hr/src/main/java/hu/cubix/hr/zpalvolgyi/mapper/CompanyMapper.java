package hu.cubix.hr.zpalvolgyi.mapper;

import hu.cubix.hr.zpalvolgyi.dto.CompanyDto;
import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.model.Company;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    public CompanyDto companyToDto(Company company);

    public List<CompanyDto> companiesToDtos(List<Company> companies);

    public Company dtoToCompany(CompanyDto companyDto);
}
