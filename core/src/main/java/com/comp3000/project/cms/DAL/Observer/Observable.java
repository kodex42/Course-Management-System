package com.comp3000.project.cms.DAL.Observer;

public interface Observable {

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    boolean hasObserver(Observer o);

    void notifyObservers(Object arg);
}
