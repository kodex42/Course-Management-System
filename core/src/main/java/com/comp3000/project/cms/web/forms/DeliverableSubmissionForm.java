package com.comp3000.project.cms.web.forms;

/* Read-Only POJO for controllers
{
        "files" : "{files} (string for now)",
        "comments" : "blah blah blah"
}
*/
public class DeliverableSubmissionForm extends Form {
    private String files;
    private String comments;

    public String getFiles() {
        return files;
    }

    public String getComments() {
        return comments;
    }
}
