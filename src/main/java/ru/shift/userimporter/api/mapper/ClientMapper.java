package ru.shift.userimporter.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.shift.userimporter.api.dto.ClientResponse;
import ru.shift.userimporter.core.model.User;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "name", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "middleName", target = "middleName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "creationTime", target = "createdAt")
    @Mapping(source = "updateTime", target = "updatedAt")
    ClientResponse userToClientResponse(User user);
}
