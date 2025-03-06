package co.edu.uniquindio.ingesis.restful.mappers;

import co.edu.uniquindio.ingesis.restful.domain.Project;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "id", ignore = true) // Se ignora porque es generado automáticamente
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // Se asigna la fecha actual
    Project toEntity(ProjectCreateRequest request);

    ProjectResponse toResponse(Project project);

    @Mapping(target = "id", ignore = true) // No se debe modificar el ID del proyecto
    @Mapping(target = "createdAt", ignore = true) // La fecha de creación no cambia
    void updateEntityFromRequest(ProjectUpdateRequest request, @MappingTarget Project project);
}
