package com.comp3000.project.cms.DAL.services.Course;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.BLL.converters.FormCourseConverter;
import com.comp3000.project.cms.exception.CannotDeleteException;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.web.forms.CourseForm;
import com.comp3000.project.cms.DAL.repository.CourseRepository;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.stream.StreamSupport;

@Service
public class CourseCommandService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseQueryService courseQueryService;
    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;
    @Autowired
    private FormCourseConverter formCourseConverter;

    public Course createCourse(CourseForm courseForm) throws FieldNotValidException, EntityExistsException {

        Course course = formCourseConverter.convert(courseForm);

        if (courseRepository.existsByCode(course.getCode()))
            throw new EntityExistsException("Course with specified code already exists");

        return courseRepository.save(course);
    }

    public void removeById(Integer id) throws NotFoundException, CannotDeleteException {
        if(!courseRepository.existsById(id))
            throw new NotFoundException("Course with specified code was not found");

        if(StreamSupport.stream(courseOfferingQueryService.getAllByCourse(courseQueryService.getById(id)).spliterator(),false).findAny().isPresent())
            throw new CannotDeleteException("Cannot delete course with existing offerings");

        courseRepository.deleteById(id);
    }
}
