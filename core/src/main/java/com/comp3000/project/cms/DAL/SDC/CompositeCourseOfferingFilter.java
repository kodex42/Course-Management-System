package com.comp3000.project.cms.SDC;

import java.util.List;

public class CompositeCourseOfferingFilter implements CourseOfferingFilterStrategy {
    private List<CourseOfferingFilterStrategy> strategies;

    public CompositeCourseOfferingFilter(List<CourseOfferingFilterStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public void filterCourseOfferingListings(List<CourseOfferingListing> courseOfferingListings) {
        for (CourseOfferingFilterStrategy strategy : this.strategies) {
            strategy.filterCourseOfferingListings(courseOfferingListings);
        }
    }
}
