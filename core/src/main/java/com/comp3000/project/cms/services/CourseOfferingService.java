package com.comp3000.project.cms.services;

import com.comp3000.project.cms.repository.CourseOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseOfferingService {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
}
