package com.comp3000.project.cms.DAL.services;

import com.comp3000.project.cms.DAL.Observer.Observable;
import com.comp3000.project.cms.DAL.Observer.Observer;
import com.comp3000.project.cms.DAL.repository.EventRepository;
import com.comp3000.project.cms.DAO.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventLoggerService implements Observer {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void Update(Observable o, Object arg) {
        if (!(arg instanceof Event)) return;

        eventRepository.save((Event)arg);
    }

    public Iterable<Event> getAll() { return eventRepository.findAll(); }
}
