package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.*;

import java.util.List;

public interface IProjectService {
    ProjectResponse createProject(ProjectCreateRequest request);

    ProjectResponse getProjectById(Long id);

    List<ProjectResponse> getAllProjects(int page, int size);

    ProjectResponse updateProject(Long id, ProjectUpdateRequest request);

    void deleteProject(Long id);

    ProjectRunResponse runProject(Long id);

    List<ReportResponse> getProjectsReportByAuthor(int page, int size, Long ownerId);

    List<ProjectResponse> getProjectsByAuthor(int page, int size, Long ownerId);

    List<ReportResponse> getProjectsReport(int page, int size);
}
