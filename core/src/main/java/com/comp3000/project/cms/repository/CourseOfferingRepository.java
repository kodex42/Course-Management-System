package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import org.springframework.data.repository.CrudRepository;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer> {

    Iterable<CourseOffering> findAllByCourse(Course course);

    Iterable<CourseOffering> findAllByCourseAndTermAndProfessor(Course course, Term term, User user);

}
