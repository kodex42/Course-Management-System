package com.comp3000.project.cms.services;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.repository.CourseRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(CourseForm courseForm) throws FieldNotValidException{

        Course course = new Course();

        if (courseRepository.existsByCode(courseForm.getCode()))
            throw new FieldNotValidException("course", "code", "Course with specified code already exists");

        course.setCode(courseForm.getCode());
        course.setName(courseForm.getName());
        course.setDescription(courseForm.getDescription());

        for(String prereq: courseForm.getPrerequisites()){
            Optional<Course> prereqCourse = courseRepository.findByCode(prereq);

            if(!prereqCourse.isPresent() || prereq.equals(courseForm.getCode()))
                throw new FieldNotValidException("course", "prerequisites", "Invalid prerequisites course code(s)");

            course.getPrerequisites().add(prereqCourse.get());
        }

        for(String precl: courseForm.getPreclusions()){
            Optional<Course> preclCourse = courseRepository.findByCode(precl);

            if(!preclCourse.isPresent() || precl.equals(courseForm.getCode()))
                throw new FieldNotValidException("course", "preclusions", "Invalid preclusions course code(s)");

            course.getPreclusions().add(preclCourse.get());
        }

        return courseRepository.save(course);
    }

    public Iterable<Course> getAll(){
        return courseRepository.findAll();
    }

    public Course getById(Integer id) throws NotFoundException{
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course with specified ID was not found"));
    }

    public Course getByCode(String code) throws NotFoundException{
        return courseRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Course with specified code was not found"));
    }

    public void removeById(Integer id) throws NotFoundException{
        if(!courseRepository.existsById(id))
            throw new NotFoundException("Course with specified code was not found");

        courseRepository.deleteById(id);
    }
}
