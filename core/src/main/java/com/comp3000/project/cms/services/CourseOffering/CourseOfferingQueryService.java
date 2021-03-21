package com.comp3000.project.cms.services.CourseOffering;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.repository.CourseOfferingRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class CourseOfferingQueryService {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public boolean existsDuplicate(CourseOffering courseOffering){
        return StreamSupport.stream(courseOfferingRepository.findAll().spliterator(), false)
            .anyMatch((x) -> x.getCourse().getId().equals(courseOffering.getCourse().getId()) &&
                             x.getTerm().getId().equals(courseOffering.getTerm().getId()) &&
                             x.getProfessor().getId().equals(courseOffering.getProfessor().getId()));
    }

    public Iterable<CourseOffering> getAll(){
        return courseOfferingRepository.findAll();
    }

    public Iterable<CourseOffering> getAllByCourse(Course course){
        return courseOfferingRepository.findAllByCourse(course);
    }

    public CourseOffering getById(Integer id) throws NotFoundException {
        return courseOfferingRepository.findById(id).orElseThrow(() -> new NotFoundException("Course offering with specified ID was not found"));
    }

}
