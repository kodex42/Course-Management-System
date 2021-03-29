package com.comp3000.project.cms;

import com.comp3000.project.cms.BusinessLogic.CourseRegistrationBL;
import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;
import junit.framework.TestCase;

import java.sql.Date;
import java.util.Collections;

public class CourseRegistrationBLTest extends TestCase{

    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;

    private Term term1;
    private Term term2;

    private CourseOffering courseOffering1;

    private User student;

    {
        course1 = new Course();
        course1.setId(1);
        course1.setCode("COMP 1005");

        course2 = new Course();
        course2.setId(2);
        course2.setCode("COMP 1006");

        course3 = new Course();
        course3.setId(3);
        course3.setCode("COMP 1405");

        course4 = new Course();
        course4.setId(4);
        course4.setCode("COMP 1406");

        course2.getPrerequisites().add(course1);
        course4.getPrerequisites().add(course3);

        course3.getPreclusions().add(course1);
        course1.getPreclusionOf().add(course3);

        course4.getPreclusions().add(course2);
        course2.getPreclusionOf().add(course4);

        term1 = new Term();
        term1.setStartDate(new Date(2020, 9, 5));
        term1.setRegistrationDeadline(new Date(2020, 10, 5));

        term2 = new Term();
        term2.setStartDate(new Date(2021, 1, 5));

        courseOffering1 = new CourseOffering();

        student = new User();

    }

    public CourseRegistrationBLTest(String testName){
        super(testName);
    }

    public void testSatisfiesPrerequisites1(){
        courseOffering1.setCourse(course1);
        courseOffering1.setTerm(term1);

        student.getTakingCourseOfferings().add(courseOffering1);

        assertTrue(CourseRegistrationBL.satisfiesPrerequisites(course2, student, term2));
    }

    public void testSatisfiesPrerequisites2(){
        courseOffering1.setCourse(course1);
        courseOffering1.setTerm(term2);

        student.setTakingCourseOfferings(Collections.singletonList(courseOffering1));

        assertTrue(!CourseRegistrationBL.satisfiesPrerequisites(course2, student, term2));
    }

    public void testSatisfiesPrerequisites3(){
        courseOffering1.setCourse(course2);
        courseOffering1.setTerm(term1);

        student.setTakingCourseOfferings(Collections.singletonList(courseOffering1));

        assertTrue(!CourseRegistrationBL.satisfiesPrerequisites(course4, student, term2));
    }

    public void testSatisfiesPrerequisites4(){
        courseOffering1.setCourse(course1);
        courseOffering1.setTerm(term1);

        student.setTakingCourseOfferings(Collections.singletonList(courseOffering1));

        assertTrue(CourseRegistrationBL.satisfiesPrerequisites(course4, student, term2));
    }

    public void testSatisfiesPrerequisites5(){

        assertTrue(!CourseRegistrationBL.satisfiesPrerequisites(course4, student, term2));
    }

    public void testSatisfiesPreclusions1(){
        courseOffering1.setCourse(course1);
        courseOffering1.setTerm(term1);

        student.setTakingCourseOfferings(Collections.singletonList(courseOffering1));

        assertTrue(CourseRegistrationBL.satisfiesPreclusions(course4, student));
    }

    public void testSatisfiesPreclusions2(){
        courseOffering1.setCourse(course2);
        courseOffering1.setTerm(term1);

        student.setTakingCourseOfferings(Collections.singletonList(courseOffering1));

        assertTrue(!CourseRegistrationBL.satisfiesPreclusions(course4, student));
    }

    public void testSatisfiesPreclusions3(){

        assertTrue(CourseRegistrationBL.satisfiesPreclusions(course3, student));
    }

    public void testRegistrationopen1(){
        courseOffering1.setTerm(term1);

        assertTrue(CourseRegistrationBL.registrationOpen(courseOffering1, term1, new Date(2020,9,15)));
    }

    public void testRegistrationopen2(){
        courseOffering1.setTerm(term1);

        assertTrue(!CourseRegistrationBL.registrationOpen(courseOffering1, term1, new Date(2020,10,15)));
    }

    public void testRegistrationopen3(){
        courseOffering1.setTerm(term1);

        assertTrue(!CourseRegistrationBL.registrationOpen(courseOffering1, term2, new Date(2020,9,15)));
    }


}
