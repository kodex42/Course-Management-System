package com.comp3000.project.cms.DAL.services.CourseOffering;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAL.repository.CourseOfferingRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class CourseOfferingQueryService {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    public boolean existsDuplicate(CourseOffering courseOffering){
        return StreamSupport.stream(courseOfferingRepository.findAllByCourseAndTermAndProfessor(courseOffering.getCourse(),
                courseOffering.getTerm(), courseOffering.getProfessor()).spliterator(), false).findAny().isPresent();
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
