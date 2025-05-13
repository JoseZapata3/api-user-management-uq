package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import co.edu.uniquindio.ingesis.restful.mappers.ProjectMapper;
import co.edu.uniquindio.ingesis.restful.domain.Project;
import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.repositories.IProjectRepository;
import co.edu.uniquindio.ingesis.restful.repositories.IUserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService{

    final ProjectMapper projectMapper;
    final IProjectRepository projectRepository;
    final IUserRepository userRepository;

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        Project project = projectMapper.parseOf(request);
        User user = userRepository.findById(request.ownerID());

        if(user == null){
            throw new NotFoundException("User not found");
        }
        
        project.setOwner(user);
        projectRepository.persist(project);
        return projectMapper.toResponse(project);
    }

    @Transactional
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id);
        if(project == null){
            throw new NotFoundException("Project not found");
        }
        return projectMapper.toResponse(project);
    }

    @Transactional
    public List<ProjectResponse> getAllProjects(int page, int size) {
        return projectRepository.findAll()
                .page(page, size)
                .list()
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id);
        if( project == null){
            throw new NotFoundException("User not found for update with ID: {}"+id);
        }
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setCode(request.code());

        projectRepository.persist(project);
        return projectMapper.toResponse(project);

    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id);
        if(project == null){
            throw new NotFoundException("Project not found for update with ID: {}"+id);
        }
        projectRepository.delete(project);
    }

}
