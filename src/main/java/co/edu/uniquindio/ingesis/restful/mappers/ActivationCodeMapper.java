package co.edu.uniquindio.ingesis.restful.mappers;

import co.edu.uniquindio.ingesis.restful.dtos.ActivationCodeCreation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import co.edu.uniquindio.ingesis.restful.domain.ActivationCode;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ActivationCodeMapper {
    @Mapping(target = "id", ignore = true)
    ActivationCode parseOf(ActivationCodeCreation activationCodeDTO);
}
