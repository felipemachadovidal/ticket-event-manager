package com.compassuol.ms_event_manager.service;

import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.model.Event;
import com.compassuol.ms_event_manager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    public EventDTO createEvent(EventDTO eventDTO){
        Event event = Event.builder()
                .eventName(eventDTO.getEventName())
                .dateTime(eventDTO.getDateTime())
                .cep(eventDTO.getCep())
                .logradouro(eventDTO.getLogradouro())
                .bairro(eventDTO.getBairro())
                .build();

        Event savedEvent = eventRepository.save(event);

        return EventDTO.builder()
                .id(savedEvent.getId())
                .eventName(savedEvent.getEventName())
                .dateTime(savedEvent.getDateTime())
                .cep(savedEvent.getCep())
                .logradouro(savedEvent.getLogradouro())
                .bairro(savedEvent.getBairro())
                .build();
    }

    public Optional<Event> getEventById(String id){
        return eventRepository.findById(id);
    }






}
