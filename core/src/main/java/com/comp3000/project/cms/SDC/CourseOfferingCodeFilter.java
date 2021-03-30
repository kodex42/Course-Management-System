package com.comp3000.project.cms.SDC;

import com.comp3000.project.cms.DAC.CourseOffering;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingCodeFilter implements CourseOfferingFilterStrategy {
    private final String code;

    public CourseOfferingCodeFilter(String code) {
        this.code = code;
    }

    @Override
    public void filterCourseOfferingListings(List<CourseOfferingListing> courseOfferingListings) {
        List<CourseOfferingListing> toRemove = new ArrayList<>();
        for (CourseOfferingListing listing : courseOfferingListings) {
            CourseOffering courseOffering = listing.getMember();

            if (!courseOffering.getCourse().getCode().contains(code))
                toRemove.add(listing);
        }
        courseOfferingListings.removeAll(toRemove);
    }
}
