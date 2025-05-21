package co.edu.uniquindio.ingesis.restful.resources;

import co.edu.uniquindio.ingesis.restful.dtos.ProjectCreateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectUpdateRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ProjectResponse;
import co.edu.uniquindio.ingesis.restful.dtos.ReportResponse;
import co.edu.uniquindio.ingesis.restful.services.IProjectService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Path("/api/v1/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProjectResource {

    final IProjectService projectService;

    @POST
    public Response create(@Valid ProjectCreateRequest request) {
        var response = projectService.createProject(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        var response = projectService.getProjectById(id);
        return Response.ok(response).build();
    }

    @GET
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                            @QueryParam("size") @DefaultValue("10") int size) {
        List<ProjectResponse> projects = projectService.getAllProjects(page, size);
        return Response.ok(projects).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProjectUpdateRequest request) {
        var response = projectService.updateProject(id, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        projectService.deleteProject(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/run/{id}")
    public Response run(@PathParam("id") Long id) {
        var response = projectService.runProject(id);
        return Response.ok(response).build();
    }

    @GET
    @Path("/reports")
    public Response getReport() {
        List<ReportResponse> reportData = projectService.getProjectsReport(0, 100); // puedes parametrizar

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            JasperReportBuilder report = DynamicReports.report();

            report
                    .columns(
                            Columns.column("Title", "title", DataTypes.stringType()),
                            Columns.column("Description", "description", DataTypes.stringType()),
                            Columns.column("Code", "code", DataTypes.stringType()),
                            Columns.column("Created At", "createdAt", DataTypes.stringType()),
                            Columns.column("Owner ID", "ownerId", DataTypes.longType()),
                            Columns.column("Owner Name", "ownerName", DataTypes.stringType()),
                            Columns.column("Type", "type", DataTypes.stringType())
                    )
                    .title(Components.text("Project Report").setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                    .setDataSource(reportData)
                    .toHtml(baos);

            return Response
                    .ok(baos.toString(StandardCharsets.UTF_8))
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("Error generating report: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/reports/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getStudentReport(@PathParam("id") Long id, @QueryParam("format") @DefaultValue("html") String format) {
        List<ReportResponse> reportData = projectService.getProjectsReportByAuthor(0, 100, id); // ajusta el page/size según tu lógica

        if (reportData.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No projects found for user with ID: " + id)
                    .build();
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            JasperReportBuilder report = DynamicReports.report();

            report
                    .columns(
                            Columns.column("Title", "title", DataTypes.stringType()),
                            Columns.column("Description", "description", DataTypes.stringType()),
                            Columns.column("Code", "code", DataTypes.stringType()),
                            Columns.column("Created At", "createdAt", DataTypes.stringType()),
                            Columns.column("Owner ID", "ownerId", DataTypes.longType()),
                            Columns.column("Owner Name", "ownerName", DataTypes.stringType()),
                            Columns.column("Type", "type", DataTypes.stringType())
                    )
                    .title(Components.text("Project Report for User ID: " + id).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                    .setDataSource(reportData);

            if ("pdf".equalsIgnoreCase(format)) {
                report.toPdf(baos);
                return Response.ok(baos.toByteArray())
                        .type("application/pdf")
                        .header("Content-Disposition", "inline; filename=project-report.pdf")
                        .build();
            } else {
                report.toHtml(baos);
                return Response.ok(baos.toString(StandardCharsets.UTF_8))
                        .type(MediaType.TEXT_HTML)
                        .build();
            }

        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error generating report: " + e.getMessage())
                    .build();
        }
    }


}
