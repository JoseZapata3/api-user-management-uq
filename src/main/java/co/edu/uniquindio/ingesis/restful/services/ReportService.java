package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.domain.Project;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class ReportService implements IReportService{

    public void generateProjectReport(List<Project> projects, String outputPath) {
        try {
            DRDataSource dataSource = new DRDataSource("title", "description", "code", "createdAt", "owner", "type");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (Project p : projects) {
                dataSource.add(
                        p.getTitle(),
                        p.getDescription(),
                        p.getCode(),
                        p.getCreatedAt() != null ? p.getCreatedAt().format(formatter) : "",
                        p.getOwner() != null ? p.getOwner().getUsername() : "N/A",
                        p.getType() != null ? p.getType().name() : "N/A"
                );
            }

            ReportTemplateBuilder template = DynamicReports.template()
                    .setColumnStyle(Styles.style().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE))
                    .setTitleStyle(Styles.style().bold().setFontSize(16));

            DynamicReports.report()
                    .setTemplate(template)
                    .title(Components.text("Project Report").setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                    .columns(
                            Columns.column("Title", "title", DynamicReports.type.stringType()),
                            Columns.column("Description", "description", DynamicReports.type.stringType()),
                            Columns.column("Code", "code", DynamicReports.type.stringType()),
                            Columns.column("Created At", "createdAt", DynamicReports.type.stringType()),
                            Columns.column("Owner", "owner", DynamicReports.type.stringType()),
                            Columns.column("Type", "type", DynamicReports.type.stringType())
                    )
                    .setDataSource(dataSource)
                    .toHtml(new FileOutputStream(outputPath));

        } catch (DRException | java.io.IOException e) {
            throw new RuntimeException("Error generating report", e);
        }
    }
}
