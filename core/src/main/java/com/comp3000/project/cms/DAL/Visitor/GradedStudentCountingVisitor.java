package com.comp3000.project.cms.DAL.Visitor;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GradedStudentCountingVisitor implements Visitor {

    private final DecimalFormat DF;
    private List<String> gradedStudentsData;

    public GradedStudentCountingVisitor() {
        this.gradedStudentsData = new ArrayList<>();
        this.DF = new DecimalFormat();
        this.DF.setMaximumFractionDigits(2);
    }

    private void computeGradedStudentsData(int numStudents, int numGraded) {
        float percentage = ((float) numGraded / (float) numStudents) * 100f;
        if (Float.isNaN(percentage))
            percentage = 0f;
        this.gradedStudentsData.add(numGraded + "/" + numStudents + " (" + this.DF.format(percentage) + "%)");
    }

    public List<String> getGradedStudentsData() {
        return gradedStudentsData;
    }

    public void setGradedStudentsData(List<String> gradedStudentsData) {
        this.gradedStudentsData = gradedStudentsData;
    }

    @Override
    public void visitCourseOffering(CourseOffering courseOffering) {
        int numWDNs = courseOffering.getCourseOffrStudentEntries().stream().mapToInt(g -> g.getLetterGrade() != null && g.getLetterGrade().equals("WDN") ? 1 : 0).sum();
        int numStudents = courseOffering.getStudents().size() - numWDNs;
        int numGraded = courseOffering.getCourseOffrStudentEntries().stream().mapToInt(g -> g.getLetterGrade() != null && !g.getLetterGrade().equals("WDN") ? 1 : 0).sum();

        computeGradedStudentsData(numStudents, numGraded);
    }

    @Override
    public void visitDeliverable(Deliverable deliverable) {
        int numStudents = deliverable.getCourseOffering().getStudents().size();
        int numGraded = deliverable.getSubmissions().stream().mapToInt(s -> s.getGrade() != 0 ? 1 : 0).sum();

        computeGradedStudentsData(numStudents, numGraded);
    }
}
