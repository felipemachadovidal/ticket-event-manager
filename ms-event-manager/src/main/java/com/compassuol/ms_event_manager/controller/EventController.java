package com.compassuol.ms_event_manager.controller;

import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/create-event")
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable String id) {
        return eventService.getEventById(Long.valueOf(id))
                .map(eventDTO -> new ResponseEntity<>(eventDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
