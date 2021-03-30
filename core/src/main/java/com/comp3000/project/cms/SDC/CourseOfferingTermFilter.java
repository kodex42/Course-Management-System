package com.comp3000.project.cms.SDC;

import com.comp3000.project.cms.DAC.CourseOffering;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingTermFilter implements CourseOfferingFilterStrategy {
    private final String term;

    public CourseOfferingTermFilter(String term) {
        this.term = term;
    }

    @Override
    public void filterCourseOfferingListings(List<CourseOfferingListing> courseOfferingListings) {
        List<CourseOfferingListing> toRemove = new ArrayList<>();
        for (CourseOfferingListing listing : courseOfferingListings) {
            CourseOffering courseOffering = listing.getMember();

            if (!courseOffering.getTerm().toString().equals(term))
                toRemove.add(listing);
        }
        courseOfferingListings.removeAll(toRemove);
    }
}
