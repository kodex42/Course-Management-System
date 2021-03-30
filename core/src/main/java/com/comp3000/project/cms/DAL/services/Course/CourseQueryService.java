package com.comp3000.project.cms.DAL.services.Course;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.DAL.repository.CourseRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
