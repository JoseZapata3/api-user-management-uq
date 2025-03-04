package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;

import java.util.List;
import java.util.UUID;

public interface IProjectService {
    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse getProjectById(UUID id);

    List<ProjectResponse> getAllProjects();

    ProjectResponse updateProject(UUID id, ProjectUpdateRequest request);

    void deleteProject(UUID id);
}
