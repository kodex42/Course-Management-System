package com.comp3000.project.cms.BLL.GradeReport;

import com.comp3000.project.cms.DAL.services.Submission.SubmissionCommandService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionQueryService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.DAO.User;
import javassist.NotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GradeReportProcessorImpl extends GradeReportProcessor<Resource> {
    @Autowired
    protected UserQueryService userQueryService;
    @Autowired
    protected SubmissionQueryService submissionQueryService;
    @Autowired
    protected SubmissionCommandService submissionCommandService;
    
    private List<String> header;
    private String sheetName;

    public GradeReportProcessorImpl() {
        this(List.of("Student id", "Email", "First name", "Last name", "Submitted", "Grade"));
    }

    public GradeReportProcessorImpl(List<String> header) {
        this("Grades", header);
    }

    public GradeReportProcessorImpl(String sheetName, List<String> header) {
        this.sheetName = sheetName;
        this.header = header;
    }

    @Override
    public Resource createReport(Deliverable deliverable, List<User> students) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(this.sheetName);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < header.size(); ++i) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header.get(i));
        }

        for (int i = 0; i < students.size(); ++i) {
            Row row = sheet.createRow(i + 1);

            row.createCell(0)
                    .setCellValue(students.get(i).getId());

            row.createCell(1)
                    .setCellValue(students.get(i).getUsername());

            row.createCell(2)
                    .setCellValue(students.get(i).getFirstName());

            row.createCell(3)
                    .setCellValue(students.get(i).getLastName());

            Submission submission = deliverable.getStudentSubmission(students.get(i));
            if (submission != null) {
                row.createCell(4)
                        .setCellValue(submission.getSubmissionDttm().toString());

                row.createCell(5)
                        .setCellValue(submission.getGrade());
            } else {
                row.createCell(4)
                        .setCellValue("not submitted");

                row.createCell(5)
                        .setCellValue(0);
            }

        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            wb.write(out);
            ByteArrayInputStream stream =  new ByteArrayInputStream(out.toByteArray());

            return new InputStreamResource(stream);
        }
    }

    @Override
    public void uploadReport(Deliverable deliverable, Resource stream) throws IOException {
        Workbook wb = new XSSFWorkbook(stream.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            try {
                User student = this.userQueryService.getById((int) sheet.getRow(i).getCell(0).getNumericCellValue());
                Submission sub = this.submissionQueryService.getByDeliverableIdAndStudentId(deliverable.getId(), student.getId());

                // if there wasn't a submission skip
                if (sub == null)
                    continue;

                // grade can be 0 <= grade <= 100
                float grade = Math.max((float) sheet.getRow(i).getCell(5).getNumericCellValue(), 0);
                grade = Math.min(grade, 100);
                this.submissionCommandService.updateGrade(sub.getId(), grade);
            } catch (NotFoundException ignored) {
            }
        }
    }
}
