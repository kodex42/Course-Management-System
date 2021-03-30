package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Term;
import com.comp3000.project.cms.DAO.User;
import org.springframework.data.repository.CrudRepository;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer> {

    Iterable<CourseOffering> findAllByCourse(Course course);

    Iterable<CourseOffering> findAllByCourseAndTermAndProfessor(Course course, Term term, User user);

}
