package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    boolean existsByCode(String code);

    Optional<Course> findByCode(String code);

}
