package com.comp3000.project.cms.DAL.Visitor;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.Submission;

import java.util.*;

public class GradeStatsVisitor implements Visitor {
    private float mean;
    private float median;
    private String mode;
    private int num_grades;
    private int num_zeros;
    private int num_fails;

    public float getMean() {
        return mean;
    }

    public void setMean(float mean) {
        this.mean = mean;
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(float median) {
        this.median = median;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getNum_grades() {
        return num_grades;
    }

    public void setNum_grades(int num_grades) {
        this.num_grades = num_grades;
    }

    public int getNum_zeros() {
        return num_zeros;
    }

    public void setNum_zeros(int num_zeros) {
        this.num_zeros = num_zeros;
    }

    public int getNum_fails() {
        return num_fails;
    }

    public void setNum_fails(int num_fails) {
        this.num_fails = num_fails;
    }

    @Override
    public void visitCourseOffering(CourseOffering courseOffering) {

    }

    @Override
    public void visitDeliverable(Deliverable deliverable) {
        List<Submission> submissions = deliverable.getSubmissions();

        float gradesTotal = 0f;                             // For calculating mean
        List<Float> gradesList = new ArrayList<>();         // For calculating median
        Map<Float, Integer> gradesMap = new HashMap<>();    // For calculating mode

        for (Submission s : submissions) {
            Float grade = s.getGrade();

            if (grade == 0f) {
                this.num_zeros++;
                continue; // Skip grades of zero
            }
            if (grade < 50f)
                this.num_fails++;
            this.num_grades++;

            gradesTotal += grade;
            gradesList.add(grade);
            gradesMap.merge(grade, 1, Integer::sum); // Tracks current number of grade occurrences as an integer value
        }
        gradesList.sort(Comparator.naturalOrder());

        List<Float> mostOccurringGrades = new ArrayList<>();
        // Get the largest integer value in the map. This equates to the largest amount of occurrences of a single grade
        int i = Collections.max(gradesMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
        for (Map.Entry<Float, Integer> e : gradesMap.entrySet())
            if (e.getValue() == i)
                mostOccurringGrades.add(e.getKey());

        this.mean = submissions.size() > 0 ? gradesTotal / submissions.size() : 0;
        this.median = gradesList.get((gradesList.size() / 2) - 1);
        this.mode = mostOccurringGrades.toString().replaceFirst("^.", "").replaceFirst(".$", "");
    }
}
