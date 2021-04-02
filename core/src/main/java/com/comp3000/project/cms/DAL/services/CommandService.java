package com.comp3000.project.cms.DAL.services;

import com.comp3000.project.cms.DAL.Observer.Observable;
import com.comp3000.project.cms.DAL.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandService implements Observable {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        if(!observers.contains(o)) observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public boolean hasObserver(Observer o) {
        return observers.contains(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        observers.forEach((observer)->observer.Update(this, arg));
    }

}
