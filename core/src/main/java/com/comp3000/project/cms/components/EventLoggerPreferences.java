package com.comp3000.project.cms.components;

import com.comp3000.project.cms.DAL.services.CommandService;
import com.comp3000.project.cms.DAL.services.Course.CourseCommandService;
import com.comp3000.project.cms.DAL.services.CourseOffering.CourseOfferingCommandService;
import com.comp3000.project.cms.DAL.services.Deliverable.DeliverableCommandService;
import com.comp3000.project.cms.DAL.services.EventLoggerService;
import com.comp3000.project.cms.DAL.services.RegApplication.RegApplicationCommandService;
import com.comp3000.project.cms.DAL.services.Submission.SubmissionCommandService;
import com.comp3000.project.cms.DAL.services.Term.TermCommandService;
import com.comp3000.project.cms.DAL.services.User.UserCommandService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class EventLoggerPreferences {

    @Autowired
    private EventLoggerService eventLoggerService;
    @Autowired
    private CourseCommandService courseCommandService;
    @Autowired
    private CourseOfferingCommandService courseOfferingCommandService;
    @Autowired
    private DeliverableCommandService deliverableCommandService;
    @Autowired
    private RegApplicationCommandService regApplicationCommandService;
    @Autowired
    private UserCommandService userCommandService;
    @Autowired
    private TermCommandService termCommandService;
    @Autowired
    private SubmissionCommandService submissionCommandService;

    public Map<String, Boolean> getPrefs(){
        Map<String, Boolean> prefs = new HashMap<>();

        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                if (field.get(this) instanceof CommandService){
                    CommandService service = (CommandService) field.get(this);
                    prefs.put(field.getType().getSimpleName(), service.hasObserver(eventLoggerService));
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return prefs;
    }

    public void setPrefs(Collection<String> prefs) throws NotFoundException{
        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for(Field field: fields){
                if(field.get(this) instanceof CommandService)
                    ((CommandService)field.get(this)).deleteObserver(eventLoggerService);
            }

            for(String pref : prefs){
                CommandService service = (CommandService) Arrays.stream(fields).filter(field -> field.getType().getSimpleName().equals(pref)).findAny().orElseThrow(
                        () -> new NotFoundException("Specified preference service does not exist")
                ).get(this);

                service.addObserver(eventLoggerService);
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
