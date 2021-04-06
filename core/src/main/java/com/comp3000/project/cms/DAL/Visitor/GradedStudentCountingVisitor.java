package com.comp3000.project.cms.DAL.Visitor;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;

import java.text.DecimalFormat;
import java.util.List;

public class GradedStudentCountingVisitor implements Visitor {

    private final DecimalFormat DF;
    private String gradedStudentsData;

    public GradedStudentCountingVisitor() {
        this.DF = new DecimalFormat();
        this.DF.setMaximumFractionDigits(2);
    }

    public String getGradedStudentsData() {
        return gradedStudentsData;
    }

    public void setGradedStudentsData(String gradedStudentsData) {
        this.gradedStudentsData = gradedStudentsData;
    }

    @Override
    public void visitCourseOffering(CourseOffering courseOffering) {

    }

    @Override
    public void visitDeliverable(Deliverable deliverable) {
        int num_students = deliverable.getCourseOffering().getStudents().size();
        int num_graded = deliverable.getSubmissions().stream().mapToInt(s -> s.getGrade() != 0 ? 1 : 0).sum();
        float percentage = ((float) num_graded / (float) num_students) * 100f;

        this.gradedStudentsData = num_graded + "/" + num_students + " (" + this.DF.format(percentage) + "%)";
    }
}
