package com.comp3000.project.cms.forms;

/* Read-Only POJO for controllers
{
        "deliverable_name" : "dname",
        "files" : "{files} (string for now)",
        "text" : "instructions",
        "due_date" : "dd/MM/yyyy"
}
*/
public class DeliverableForm {
    private String deliverable_name;
    private String files; // Leave as string for now until I can figure out file controllers
    private String text;
    private String due_date;

    public String getDeliverable_name() {
        return deliverable_name;
    }

    public String getFiles() {
        return files;
    }

    public String getText() {
        return text;
    }

    public String getDue_date() {
        return due_date;
    }
}
