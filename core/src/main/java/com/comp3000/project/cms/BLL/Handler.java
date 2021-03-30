package com.comp3000.project.cms.BLL;


public abstract class Handler<T> {
    protected Handler<T> next;

    void setNext(Handler<T> next) {
        this.next = next;
    }

    public abstract Status<T> handle(T ap);
}
