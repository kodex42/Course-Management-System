package com.comp3000.project.cms.forms;

import javax.validation.constraints.NotEmpty;

/* Read-Only POJO parent class for controllers
    Forms are created by deserializing JSON from request bodies
    If any form cannot be validated as per the annotations, a JSON object will be returned specifying which fields are invalid.
{
        "invoker" : "username"
}
*/
public class Form {
    @NotEmpty(message = "Invoker not specified.")
    private String invoker;

    public String getInvoker() {
        return invoker;
    }
}
