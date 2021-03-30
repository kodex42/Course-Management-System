package com.comp3000.project.cms.web.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/* Read-Only POJO for controllers
{
        "first_name" : "fname",
        "last_name" : "lname",
        "birth_date" : "yyyy-MM-dd HH:mm:ss",
        "type" : "STUDENT or PROFESSOR"
}
*/
public class RegisterForm extends Form {
    @NotEmpty(message = "You must include your First Name.")
    private String first_name;
    @NotEmpty(message = "You must include your Last Name.")
    private String last_name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date birth_date;
    @NotEmpty(message = "Application type not specified.")
    private String type;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getType() {
        return type;
    }
}
