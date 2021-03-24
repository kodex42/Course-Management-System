package com.comp3000.project.cms.converters;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.services.Course.CourseQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormCourseConverter extends Converter<Course, CourseForm>{

    @Autowired
    private CourseQueryService courseQueryService;

    @Override
    public Course convert(CourseForm courseForm) throws FieldNotValidException {

        Course course = new Course();

        course.setCode(courseForm.getCode());
        course.setName(courseForm.getName());
        course.setDescription(courseForm.getDescription());

        for(String prereq: courseForm.getPrerequisites()){
            try{
                course.getPrerequisites().add(courseQueryService.getByCode(prereq));
            }catch (NotFoundException e){
                throw new FieldNotValidException(course, "prerequisites", "Invalid prerequisites course code(s)");
            }
        }
        for(String precl: courseForm.getPreclusions()){
            try{
                course.getPreclusions().add(courseQueryService.getByCode(precl));
            }catch (NotFoundException e){
                throw new FieldNotValidException(course, "preclusions", "Invalid preclusions course code(s)");
            }
        }

        return course;
    }
}
