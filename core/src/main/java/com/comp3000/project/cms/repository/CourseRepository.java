package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    boolean existsByCode(String code);

    Optional<Course> findByCode(String code);

}
