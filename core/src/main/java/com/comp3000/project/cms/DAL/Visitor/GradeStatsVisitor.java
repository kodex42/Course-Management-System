package com.comp3000.project.cms.DAL.Visitor;

import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.DAO.Deliverable;

public class GradeStatsVisitor implements Visitor {
    private float mean;
    private float median;
    private float mode;
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

    public float getMode() {
        return mode;
    }

    public void setMode(float mode) {
        this.mode = mode;
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

    }
}
