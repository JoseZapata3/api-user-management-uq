package co.edu.uniquindio.ingesis.restful.mappers;

import org.mapstruct.factory.Mappers;

public interface SampleMapper {
    SampleMapper INSTANCE = Mappers.getMapper(SampleMapper.class);
}
