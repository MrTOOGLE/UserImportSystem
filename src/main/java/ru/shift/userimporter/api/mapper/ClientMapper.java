package ru.shift.userimporter.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shift.userimporter.api.dto.ClientResponse;
import ru.shift.userimporter.core.model.User;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "createdAt", target = "creationTime")
    @Mapping(source = "updatedAt", target = "updateTime")
    ClientResponse userToClientResponse(User user);
}
