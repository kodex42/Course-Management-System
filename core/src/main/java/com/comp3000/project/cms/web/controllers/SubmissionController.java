package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.BLL.decorators.SubmissionRenderDecorator;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.DAL.services.Deliverable.DeliverableQueryService;
import com.comp3000.project.cms.DAL.services.StorageService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionCommandService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionQueryService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.web.forms.DeliverableGradeForm;
import javassist.NotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course_offerings/{courseOffrId}/deliverables/{delivId}/submissions")
public class SubmissionController {
    private static final Logger log = LoggerFactory.getLogger(CourseOfferingController.class);

    @Autowired
    private SubmissionQueryService submissionQueryService;
    @Autowired
    private DeliverableQueryService deliverableQueryService;
    @Autowired
    private SubmissionCommandService submissionCommandService;
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private StorageService storageService;

    @GetMapping
    public String getSubmissions(@PathVariable Integer courseOffrId,
                                 @PathVariable Integer delivId,
                                 Model model) {
        try {
            CourseOffering courseOffering = this.courseOfferingQueryService.getById(courseOffrId);
            List<User> students = courseOffering.getStudents();
            Deliverable deliverable = this.deliverableQueryService.getById(delivId);

            model.addAttribute("courseOffering", courseOffering);
            model.addAttribute("students", students);
            model.addAttribute("deliverable", deliverable);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "submissions";
    }
    @PutMapping("/{subId}")
    public String editSubmissionGrade(@PathVariable Integer courseOffrId,
                                      @PathVariable Integer delivId,
                                      @PathVariable Integer subId,
                                      @RequestParam("grade") Float grade,
                                      Model model) {
        try {
            this.submissionCommandService.updateGrade(subId, grade);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "redirect:/course_offerings/" + courseOffrId
                +"/deliverables/" + delivId + "/submissions";
    }

    @GetMapping("/{subId}/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename,
                                              @PathVariable Integer subId,
                                              @PathVariable Integer delivId) {
        try {
            Submission submission = this.submissionQueryService.getById(subId);
            User student = submission.getStudent();
            Deliverable deliverable = this.deliverableQueryService.getById(delivId);

            // TODO: need a more clever way
            Path prefix = Paths.get(
                    deliverable.getCourseOffering().toString(),
                    "submissions"
            );
            String requestedFilename = student.getUsername() + "_" + filename;
            Resource file = storageService.loadAsResource(
                    prefix.toString(),
                    requestedFilename
            );

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"")
                    .contentLength(file.contentLength())
                    .body(file);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/grades")
    public ResponseEntity<Resource> getBulkDeliverableGrades(@PathVariable Integer courseOffrId,
                                                                  @PathVariable Integer delivId) {
        try {
            CourseOffering courseOffering = this.courseOfferingQueryService.getById(courseOffrId);
            Deliverable deliverable = this.deliverableQueryService.getById(delivId);
            List<User> students = courseOffering.getStudents();

            List<String> header = List.of("Student id", "Email", "First name", "Last name", "Submitted", "Grade");

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Grades");
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

            String filename = "grades.xlsx";
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                wb.write(out);
                ByteArrayInputStream stream =  new ByteArrayInputStream(out.toByteArray());
                InputStreamResource report = new InputStreamResource(stream);

                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .body(report);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/grades")
    public String submitBulkDeliverableGrades(@PathVariable Integer courseOffrId,
                                                            @PathVariable Integer delivId,
                                                            @RequestParam("file") MultipartFile file) {
        try {
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                try {
                    User student = this.userQueryService.getById((int) sheet.getRow(i).getCell(0).getNumericCellValue());
                    Submission sub = this.submissionQueryService.getByDeliverableIdAndStudentId(delivId, student.getId());

                    int grade = (int) sheet.getRow(i).getCell(5).getNumericCellValue();
                    this.submissionCommandService.updateGrade(sub.getId(), grade);
                } catch (NotFoundException ex) {
                    continue;
                }
            }


        } catch (Exception e) {
            log.error(e.toString());
        }

        return "redirect:/course_offerings/" + courseOffrId
                +"/deliverables/" + delivId + "/submissions";
    }
}
