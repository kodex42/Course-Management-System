package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.BLL.GradeReport.GradeReportProcessor;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.DAL.services.Deliverable.DeliverableQueryService;
import com.comp3000.project.cms.DAL.services.StorageService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionCommandService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionQueryService;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;
import com.comp3000.project.cms.DAO.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    private StorageService storageService;
    @Autowired
    private GradeReportProcessor<Resource> gradeReportProcessor;

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

        return String.format("redirect:/course_offerings/%d/deliverables/%d/submissions", courseOffrId, delivId);
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


            Resource report = this.gradeReportProcessor.createReport(deliverable, students);
            String filename = "grades.xlsx";


            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(report);
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
            Deliverable deliverable = this.deliverableQueryService.getById(delivId);
            this.gradeReportProcessor.uploadReport(deliverable, file.getResource());
        } catch (Exception e) {
            log.error(e.toString());
        }

        return String.format("redirect:/course_offerings/%d/deliverables/%d/submissions", courseOffrId, delivId);
    }
}
