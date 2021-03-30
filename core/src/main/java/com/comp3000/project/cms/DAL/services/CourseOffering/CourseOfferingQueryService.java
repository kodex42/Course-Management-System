package com.comp3000.project.cms.DAL.services.CourseOffering;

import com.comp3000.project.cms.DAO.Course;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAL.repository.CourseOfferingRepository;
import com.comp3000.project.cms.DAL.SDC.CourseOfferingFilterStrategy;
import com.comp3000.project.cms.DAL.SDC.CourseOfferingListing;
import com.comp3000.project.cms.DAL.SDC.ListingMapper;
import com.comp3000.project.cms.BLL.converters.FormFilterConverter;
import com.comp3000.project.cms.web.forms.FilterForm;
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
