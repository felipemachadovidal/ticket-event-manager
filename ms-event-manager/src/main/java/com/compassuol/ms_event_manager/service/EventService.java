package com.compassuol.ms_event_manager.service;

import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.model.Event;
import com.compassuol.ms_event_manager.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventDTO.class);
    }


    public Optional<EventDTO> getEventById(String id) {
        return eventRepository.findById(id)
                .map(event -> modelMapper.map(event, EventDTO.class));
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public List<EventDTO> getAllEventsSorted() {
        return eventRepository.findAll(Sort.by(Sort.Order.asc("eventName"))).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public EventDTO updateEvent(String id, EventDTO eventDTO) {
        if (eventRepository.existsById(id)) {
            Event event = modelMapper.map(eventDTO, Event.class);
            event.setId(id);
            Event updatedEvent = eventRepository.save(event);
            return modelMapper.map(updatedEvent, EventDTO.class);
        } else {
            return null;
        }


    }






}
