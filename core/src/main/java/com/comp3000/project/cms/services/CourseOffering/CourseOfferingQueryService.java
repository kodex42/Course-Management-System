package com.comp3000.project.cms.services.CourseOffering;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.SDC.CourseOfferingFilterStrategy;
import com.comp3000.project.cms.SDC.CourseOfferingListing;
import com.comp3000.project.cms.SDC.ListingMapper;
import com.comp3000.project.cms.converters.FormFilterConverter;
import com.comp3000.project.cms.forms.FilterForm;
import com.comp3000.project.cms.repository.CourseOfferingRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CourseOfferingQueryService {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private ListingMapper listingMapper;
    @Autowired
    private FormFilterConverter formFilterConverter;

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

    public Iterable<CourseOfferingListing> getAllWithFilters(FilterForm filterForm) {
        List<CourseOfferingListing> courseOfferingListings = listingMapper.mapCourseOfferings(getAll());

        if (filterForm.hasFilters()) {
            CourseOfferingFilterStrategy filterStrategy = formFilterConverter.convert(filterForm);
            filterStrategy.filterCourseOfferingListings(courseOfferingListings);
        }

        return courseOfferingListings;
    }
}
