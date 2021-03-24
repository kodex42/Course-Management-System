package com.comp3000.project.cms.exception;

public class FieldNotValidException extends Exception{

    private String field;
    private String code;

    public FieldNotValidException(Object object, String field, String message){
        super(message);
        this.field = field;
        this.code = "error" + "." + object.getClass().getName().toLowerCase() + "." + field;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }
}
