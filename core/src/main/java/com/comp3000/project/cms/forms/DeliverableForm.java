package com.comp3000.project.cms.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.util.Date;

/* Read-Only POJO for controllers
{
        "deliverable_name" : "dname",
        "files" : "{files} (string for now)",
        "text" : "instructions",
        "due_date" : "yyyy-MM-dd HH:mm:ss"
}
*/
public class DeliverableForm {
    private String deliverable_name;
    private String files; // Leave as string for now until I can figure out file controllers
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date due_date;

    public String getDeliverable_name() {
        return deliverable_name;
    }

    public String getFiles() {
        return files;
    }

    public String getText() {
        return text;
    }

    public Date getDue_date() {
        return due_date;
    }
}
