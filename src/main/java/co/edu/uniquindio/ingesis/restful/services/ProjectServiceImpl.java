package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import co.edu.uniquindio.ingesis.restful.mappers.ProjectMapper;
import co.edu.uniquindio.ingesis.restful.repositories.IProjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService{

    final ProjectMapper projectMapper;
    final IProjectRepository projectrRepository;

    @Override
    public ProjectResponse updateProject(UUID id, ProjectUpdateRequest request) {
        return null;
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return null;
    }

    @Override
    public void deleteProject(UUID id) {

    }

    @Override
    public ProjectResponse getProjectById(UUID id) {
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectCreateRequest request) {
        return null;
    }
}
