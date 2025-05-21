package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.Project;

import java.util.List;

public interface IReportService {
    void generateProjectReport(List<Project> projects, String outputPath);
}
