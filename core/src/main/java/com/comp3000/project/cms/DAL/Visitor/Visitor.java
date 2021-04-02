package com.comp3000.project.cms.DAL.Visitor;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;

public interface Visitor {

    void visitCourseOffering(CourseOffering courseOffering);
    void visitDeliverable(Deliverable deliverable);
}
