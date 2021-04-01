package com.comp3000.project.cms.web.controllers;

import com.comp3000.project.cms.BLL.Status;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionCommandService;
import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import com.comp3000.project.cms.DAO.*;
import com.comp3000.project.cms.BLL.converters.FormDeliverableConverter;
import com.comp3000.project.cms.web.forms.DeliverableForm;
import com.comp3000.project.cms.web.forms.DeliverableGradeForm;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.DAL.services.Deliverable.DeliverableCommandService;
import com.comp3000.project.cms.DAL.services.Deliverable.DeliverableQueryService;
import com.comp3000.project.cms.DAL.services.StorageService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/*  DeliverableController

    Handles the following routes:
        GET
            /courses-offerings/{id}/deliverables/create
            /courses-offerings/{id}/deliverables/{deliverable_name}
        POST
            /courses-offerings/{id}/deliverables
        DELETE
            /courses-offerings/{id}/deliverables/{deliverable_name}
        PUT
            /courses-offerings/{id}/deliverables/{deliverable_name}
            /courses-offerings/{id}/deliverables/{deliverable_name}/grades
*/
@Validated // Used for validating collections
@Controller
@RequestMapping("/course_offerings/{courseOffrId}/deliverables")
public class DeliverableController {

    private static final Logger log = LoggerFactory.getLogger(DeliverableController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private SubmissionCommandService submissionCommandService;
    @Autowired
    private DeliverableCommandService deliverableCommandService;
    @Autowired
    private DeliverableQueryService deliverableQueryService;
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private FormDeliverableConverter formDeliverableConverter;
    @Autowired
    private UserQueryService userQueryService;

    @GetMapping("/create")
    public String createDeliverable(@PathVariable Integer courseOffrId,
                                    Model model) {
        try {
            DeliverableForm form = new DeliverableForm();
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            form.setCourseOffr(courseOffering);
            model.addAttribute("deliverable", form);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "course_deliverable";
    }

    @GetMapping("/{deliverableId}")
    public String editDeliverable(@PathVariable Integer deliverableId,
                                    Model model) {
        try {
            Deliverable deliv = this.deliverableQueryService.getById(deliverableId);
            DeliverableForm form = new DeliverableForm(deliv);
            model.addAttribute("deliverable", form);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "course_deliverable";
    }


    @PostMapping()
    public String addDeliverable(Model model,
                                 @ModelAttribute DeliverableForm deliverable,
                                 Principal principal,
                                 @PathVariable Integer courseOffrId) {
        log.info("Request to add deliverable received");

        try {
            User user = userQueryService.getByUsername(principal.getName());
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            deliverable.setAuthor(user);
            deliverable.setCourseOffr(courseOffering);

            Deliverable delObj = formDeliverableConverter.covert(deliverable);
            if (deliverable.hasFile()) {
                storageService.save(courseOffering.toString(), deliverable.getFile());
                // override the file with a new one if it was updated
                delObj.setFilename(deliverable.getFile().getOriginalFilename());
            }

            deliverableCommandService.create(delObj);
            Status<DeliverableForm> status = Status.ok(deliverable);

            DeliverableForm form = new DeliverableForm();
            form.setCourseOffr(courseOffering);
            model.addAttribute("status", status);
            model.addAttribute("deliverable", form);

            return "redirect:/course_offerings/"+deliverable.getCourseOffering().getId();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return "course_deliverable";
    }

    @GetMapping("/{delivId}/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename,
                                              @PathVariable Integer delivId,
                                              @PathVariable Integer courseOffrId) {
        try {
            CourseOffering courseOffering = courseOfferingQueryService.getById(courseOffrId);
            Resource file = storageService.loadAsResource(courseOffering.toString(), filename);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"")
                    .contentLength(file.contentLength())
                    .body(file);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{delivId}")
    public String removeDeliverable(@PathVariable Integer delivId,
                                            @PathVariable Integer courseOffrId) {
        log.info("Request to delete deliverable " + delivId + " from course offering" + courseOffrId + " received");

        try {
            this.deliverableCommandService.delete(delivId);
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "redirect:/course_offerings/"+courseOffrId;
    }

    @PostMapping("/{delivId}")
    public String submitDeliverable(@PathVariable Integer courseOffrId,
                                    Principal principal,
                                    @RequestParam("file") MultipartFile file,
                                    @PathVariable Integer delivId) {
        try {
            if (!file.isEmpty()) {
                User user = userQueryService.getByUsername(principal.getName());
                Deliverable deliverable = this.deliverableQueryService.getById(delivId);

                // TODO: need a more clever way
                Path prefix = Paths.get(
                        deliverable.getCourseOffering().toString(),
                        "submissions"
                );
                String filename = user.getUsername() + "_" + file.getOriginalFilename();
                storageService.save(prefix.toString(), file, filename);

                Submission submission = new Submission();
                submission.setDeliverable(deliverable);
                if (deliverable.getStudentSubmission(user) != null)
                    submission.setId(deliverable.getStudentSubmission(user).getId());

                submission.setFilename(file.getOriginalFilename());
                submission.setStudent(user);
                submission.setSubmissionDttm(new Date());

                submissionCommandService.create(submission);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return "redirect:/course_offerings/" + courseOffrId;
    }
}
