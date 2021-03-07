package com.comp3000.project.cms.forms;

/* Read-Only POJO for controllers
{
        "first_name" : "fname",
        "last_name" : "lname",
        "birth_date" : "dd/MM/yyyy",
        "type" : "STUDENT or PROFESSOR"
}
*/
public class RegisterForm {
    private String first_name;
    private String last_name;
    private String birth_date; // Optional; may be ""
    private String type;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getType() {
        return type;
    }
}
