package hu.cubix.hr.zpalvolgyi.mapper;

import hu.cubix.hr.zpalvolgyi.dto.EmployeeDto;
import hu.cubix.hr.zpalvolgyi.dto.TimeOffRequestDto;
import hu.cubix.hr.zpalvolgyi.model.Employee;
import hu.cubix.hr.zpalvolgyi.model.TimeOffRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeOffRequestMapper {

    @Mapping(target = "requestedBy", source = "requestedBy.name")
    @Mapping(target = "approver", source = "approver.name")
    public TimeOffRequestDto timeOffRequestToDto(TimeOffRequest timeOffRequest);

    @InheritInverseConfiguration
    public TimeOffRequest dtoTotimeOffRequest(TimeOffRequestDto timeOffRequestDto);

    public List<TimeOffRequestDto> timeOffRequestsToDtos(List<TimeOffRequest> timeOffRequests);

    public List<TimeOffRequest> dtosTotimeOffRequests(List<TimeOffRequestDto> timeOffRequestDtos);
}
