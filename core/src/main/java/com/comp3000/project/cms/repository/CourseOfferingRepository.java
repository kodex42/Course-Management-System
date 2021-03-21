package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import org.springframework.data.repository.CrudRepository;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer> {

    public Iterable<CourseOffering> findAllByCourse(Course course);

}
