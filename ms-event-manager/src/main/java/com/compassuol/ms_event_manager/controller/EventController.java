package com.compassuol.ms_event_manager.controller;

import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventmanagement/v1")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping("/create-event")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventCreateRequestDTO eventRequest) {
        EventDTO createdEvent = eventService.createEvent(eventRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @GetMapping("/get-event")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable String eventId) {
        return eventService.getEventById((eventId))
                .map(eventDTO -> new ResponseEntity<>(eventDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<EventDTO>> getAllEventsSorted() {
        List<EventDTO> sortedEvents = eventService.getAllEventsSorted();
        return ResponseEntity.ok(sortedEvents);
    }

}
