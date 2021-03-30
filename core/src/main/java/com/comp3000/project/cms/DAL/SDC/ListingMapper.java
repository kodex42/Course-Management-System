package com.comp3000.project.cms.DAL.SDC;

import com.comp3000.project.cms.DAO.CourseOffering;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListingMapper {
    public List<CourseOfferingListing> mapCourseOfferings(Iterable<CourseOffering> courseOfferings) {
        List<CourseOfferingListing> courseOfferingListings = new ArrayList<>();

        for (CourseOffering offering : courseOfferings)
            courseOfferingListings.add(new CourseOfferingListing(offering));

        return courseOfferingListings;
    }
}
