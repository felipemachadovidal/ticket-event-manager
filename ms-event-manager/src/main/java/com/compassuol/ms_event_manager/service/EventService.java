package com.compassuol.ms_event_manager.service;

import com.compassuol.ms_event_manager.client.ViaCepClient;
import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
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
    private final ViaCepClient viaCepClient;

    @Autowired
    public EventService(EventRepository eventRepository, ModelMapper modelMapper, ViaCepClient viaCepClient) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.viaCepClient = viaCepClient;
    }


    public EventDTO createEvent(EventCreateRequestDTO eventRequest) {
        var viaCepResponse = viaCepClient.getCepDetails(eventRequest.getCep());

        if (viaCepResponse == null) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado: " + eventRequest.getCep());
        }

        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventName(eventRequest.getEventName());
        eventDTO.setEventDateTime(eventRequest.getDateTime());
        eventDTO.setCep(eventRequest.getCep());
        eventDTO.setLogradouro(viaCepResponse.getLogradouro());
        eventDTO.setCidade(viaCepResponse.getCidade());
        eventDTO.setUf(viaCepResponse.getUf());
        eventDTO.setDeleted(false);

        Event event = modelMapper.map(eventDTO, Event.class);
        Event savedEvent = eventRepository.save(event);

        return modelMapper.map(savedEvent, EventDTO.class);
    }



    public Optional<EventDTO> getEventById(Long id) {
        return eventRepository.findById(id)
                .map(event -> modelMapper.map(event, EventDTO.class));
    }


    public List<EventDTO> getAllEvents() {
        return eventRepository.findByDeletedFalse(Sort.by(Sort.Order.asc("eventName"))).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }


    public List<EventDTO> getAllEventsSorted() {
        return eventRepository.findByDeletedFalse(Sort.by(Sort.Order.asc("eventName"))).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }


    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();

            if (!event.getCep().equals(eventDTO.getCep())) {
                var viaCepResponse = viaCepClient.getCepDetails(eventDTO.getCep());
                eventDTO.setLogradouro(viaCepResponse.getLogradouro());
                eventDTO.setCidade(viaCepResponse.getCidade());
                eventDTO.setUf(viaCepResponse.getUf());
            }

            modelMapper.map(eventDTO, event);
            Event updatedEvent = eventRepository.save(event);
            return modelMapper.map(updatedEvent, EventDTO.class);
        }
        return null;}


    public boolean softDeleteEvent(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.setDeleted(true);
            eventRepository.save(event);
            return true;
        }
        return false;
    }
}
