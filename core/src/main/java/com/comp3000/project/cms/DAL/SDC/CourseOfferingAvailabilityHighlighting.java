package com.comp3000.project.cms.DAL.SDC;

import com.comp3000.project.cms.BLL.CourseRegistrationBL;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.components.CMS;

import java.awt.*;
import java.util.List;

public class CourseOfferingAvailabilityHighlighting implements CourseOfferingFilterStrategy {
    private CourseOfferingFilterStrategy strategy;
    private CMS cms;
    private User student;

    public CourseOfferingAvailabilityHighlighting(CourseOfferingFilterStrategy strategy, CMS cms, User student) {
        this.strategy = strategy;
        this.cms = cms;
        this.student = student;
    }

    @Override
    public void filterCourseOfferingListings(List<CourseOfferingListing> courseOfferingListings) {
        strategy.filterCourseOfferingListings(courseOfferingListings);

        for (CourseOfferingListing listing : courseOfferingListings) {
            CourseOffering courseOffering = listing.getMember();
            if (!CourseRegistrationBL.registrationOpen(courseOffering, cms.getCurrentTerm(), cms.getCurrentTime()))
                listing.setColor(Color.GRAY);
            else if (CourseRegistrationBL.isRegistered(courseOffering, this.student))
                listing.setColor(Color.RED);
            else if (!CourseRegistrationBL.satisfiesPrerequisites(courseOffering.getCourse(), this.student, cms.getCurrentTerm()))
                listing.setColor(Color.YELLOW);
            else if (!CourseRegistrationBL.satisfiesPreclusions(courseOffering.getCourse(), this.student))
                listing.setColor(Color.MAGENTA);
            else if (CourseRegistrationBL.maxCapacityReached(courseOffering))
                listing.setColor(Color.BLUE);
            else
                listing.setColor(Color.GREEN);
        }
    }
}
