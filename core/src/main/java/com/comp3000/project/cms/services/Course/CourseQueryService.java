package com.comp3000.project.cms.services.Course;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.forms.CourseForm;
import com.comp3000.project.cms.repository.CourseRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseQueryService {

    @Autowired
    private CourseRepository courseRepository;

    public Iterable<Course> getAll(){
        return courseRepository.findAll();
    }

    public Course getById(Integer id) throws NotFoundException{
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course with specified ID was not found"));
    }

    public Course getByCode(String code) throws NotFoundException{
        return courseRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Course with specified code was not found"));
    }
}
