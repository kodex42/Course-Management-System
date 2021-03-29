package com.comp3000.project.cms.BusinessLogic;

public class Status<T> {
    private String error;
    private boolean successful;
    private T obj;
    private String text;

    private Status(T obj, String error, boolean successful) {
        this.error = error;
        this.successful = successful;
        this.obj = obj;
    }

    public static <T> Status<T> ok(T obj) {
        return new Status<T>(obj, "", true);
    }

    public static <T> Status<T> failed(T obj, String error) {
        return new Status<T>(obj, error, false);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccessful() {
        return successful;
    }
}


