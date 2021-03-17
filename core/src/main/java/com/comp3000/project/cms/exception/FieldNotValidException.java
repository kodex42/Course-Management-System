package com.comp3000.project.cms.exception;

public class FieldNotValidException extends Exception{

    private String field;
    private String code;

    public FieldNotValidException(String object, String field, String message){
        super(message);
        this.field = field;
        this.code = "error" + "." + object + "." + field;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }
}
