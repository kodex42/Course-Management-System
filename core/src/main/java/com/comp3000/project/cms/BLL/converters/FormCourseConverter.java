package com.comp3000.project.cms.BLL.converters;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.web.forms.CourseForm;
import com.comp3000.project.cms.DAL.services.Course.CourseQueryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormCourseConverter implements Converter<CourseForm, Course> {

    @Autowired
    private CourseQueryService courseQueryService;

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
