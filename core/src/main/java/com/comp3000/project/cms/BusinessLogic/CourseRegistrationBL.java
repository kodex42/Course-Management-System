package com.comp3000.project.cms.BusinessLogic;

import com.comp3000.project.cms.DAC.Course;
import com.comp3000.project.cms.DAC.CourseOffering;
import com.comp3000.project.cms.DAC.Term;
import com.comp3000.project.cms.DAC.User;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseRegistrationBL {

    public static boolean registrationOpen(CourseOffering courseOffr, Term curTerm, Date curTime){
        if(courseOffr.getTerm().compareTo(curTerm) != 0) return false;

        return courseOffr.getTerm().getRegistrationDeadline().compareTo(curTime) > 0;
    }

    public static boolean maxCapacityReached(CourseOffering courseOffr){
        return courseOffr.getCapacity() == courseOffr.getStudents().size();
    }

    public static boolean isRegistered(CourseOffering courseOffr, User student){
        return student.getTakingCourseOfferings().stream().anyMatch((courseOffr2) -> courseOffr2.getId().equals(courseOffr.getId()));
    }

    public static boolean satisfiesPrerequisites(Course course, User student, Term curTerm){

        for(Course prereq: course.getPrerequisites()){
            if(student.getTakingCourseOfferings().stream().noneMatch((courseOffr) ->

                (prereq.getId().equals(courseOffr.getCourse().getId()) ||

                Stream.concat(prereq.getPreclusions().stream(), prereq.getPreclusionOf().stream()).anyMatch((precl) ->
                     student.getTakingCourseOfferings().stream().anyMatch((courseOffr2) -> precl.getId().equals(courseOffr2.getCourse().getId())))) &&

                 curTerm.compareTo(courseOffr.getTerm()) > 0

            )) return false;
        }
        return true;
    }

    public static boolean satisfiesPreclusions(Course course, User student){

        for(Course precl: Stream.concat(course.getPreclusions().stream(), course.getPreclusionOf().stream()).collect(Collectors.toList())){

            if(student.getTakingCourseOfferings().stream().anyMatch((courseOffr) ->
                precl.getId().equals(courseOffr.getCourse().getId())
            )) return false;
        }
        return true;
    }
}
