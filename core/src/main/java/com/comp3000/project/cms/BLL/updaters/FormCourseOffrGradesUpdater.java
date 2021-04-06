package com.comp3000.project.cms.BLL.updaters;

import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingQueryService;
import com.comp3000.project.cms.DAO.CourseOffering;
import com.comp3000.project.cms.exception.FieldNotValidException;
import com.comp3000.project.cms.web.forms.CourseOffrGradesForm;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FormCourseOffrGradesUpdater implements Updater<CourseOffering, CourseOffrGradesForm>{

    @Autowired
    private CourseOfferingQueryService courseOfferingQueryService;

    @Override
    public CourseOffering update(CourseOffering courseOffering, CourseOffrGradesForm courseOffrGradesForm) throws FieldNotValidException {

        for(Map.Entry<Integer, Float> formStudentGrade : courseOffrGradesForm.getStudentGrades().entrySet()){

            if(formStudentGrade.getValue() == null) continue;

            courseOffering.getStudentGrades().stream().filter(studentGrade -> studentGrade.getStudent().getId().equals(formStudentGrade.getKey())).findAny().orElseThrow(
                    () -> new FieldNotValidException(courseOffering, "studentGrades", "Invalid student id(s)")
            ).setGrade(formStudentGrade.getValue());
        }

        return courseOffering;
    }
}
