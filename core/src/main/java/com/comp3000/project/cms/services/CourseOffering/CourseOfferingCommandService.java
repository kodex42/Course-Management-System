package com.comp3000.project.cms.services.CourseOffering;

import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.converters.FormCourseOfferingConverter;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseOfferingForm;
import com.comp3000.project.cms.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.stream.StreamSupport;

@Service
public class CourseOfferingCommandService {
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private FormCourseOfferingConverter formCourseOfferingConverter;

    public CourseOffering createCourse(CourseOfferingForm courseOfferingForm) throws FieldNotValidException, EntityExistsException {
        CourseOffering courseOffering = formCourseOfferingConverter.convert(courseOfferingForm);

        if(courseOfferingQueryService.existsDuplicate(courseOffering))
            throw new EntityExistsException("Course offering with specified info already exists");

        return courseOfferingRepository.save(courseOffering);
    }

}
