package com.compassuol.ms_event_manager.testserivce;



import com.compassuol.ms_event_manager.client.ViaCepClient;
import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.dto.ViaCepResponse;
import com.compassuol.ms_event_manager.model.Event;
import com.compassuol.ms_event_manager.repository.EventRepository;
import com.compassuol.ms_event_manager.service.EventService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private EventService eventService;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepository, modelMapper, viaCepClient);
    }

    @Test
    void createEvent_Success() {

        EventCreateRequestDTO requestDTO = new EventCreateRequestDTO();
        requestDTO.setEventName("Music Festival");
        requestDTO.setDateTime(LocalDateTime.parse("2025-01-01T20:00:00"));
        requestDTO.setCep("01001000");

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setBairro("Sé");
        viaCepResponse.setLocalidade("São Paulo");
        viaCepResponse.setUf("SP");

        Event savedEvent = new Event();
        savedEvent.setId("1");
        savedEvent.setEventName("Music Festival");
        savedEvent.setDateTime(LocalDateTime.parse("2025-01-01T20:00:00"));
        savedEvent.setCep("01001000");
        savedEvent.setBairro("Sé");
        savedEvent.setLogradouro("São Paulo");
        savedEvent.setUf("SP");

        when(viaCepClient.getCepDetails("01001000")).thenReturn(viaCepResponse);
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        EventDTO result = eventService.createEvent(requestDTO);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Music Festival", result.getEventName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void findByEventId_Success() {
        String id = "676ee375a48b511225981d04";
        ObjectId objectId = new ObjectId(id);

        Event mockEvent = new Event();
        mockEvent.setId(id);
        mockEvent.setEventName("Sample Event");

        when(eventRepository.findByObjectId(objectId)).thenReturn(Optional.of(mockEvent));

        Optional<EventDTO> result = eventService.findByEventId(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Sample Event", result.get().getEventName());
    }

    @Test
    void getAllEvents_Success() {
        Event event1 = new Event();
        event1.setId("1");
        event1.setEventName("Music Festival");

        Event event2 = new Event();
        event2.setId("2");
        event2.setEventName("Art Expo");

        when(eventRepository.findByDeletedFalse(any(Sort.class))).thenReturn(List.of(event1, event2));

        List<EventDTO> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Music Festival", result.get(0).getEventName());
    }

    @Test
    void updateEvent_Success() {

        Event existingEvent = new Event();
        existingEvent.setId("1");
        existingEvent.setEventName("Old Event");
        existingEvent.setCep("01001000");

        EventDTO updateDTO = new EventDTO();
        updateDTO.setId("1");
        updateDTO.setEventName("Updated Event");
        updateDTO.setCep("01002000");

        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setLogradouro("Updated Street");
        viaCepResponse.setLocalidade("Updated City");
        viaCepResponse.setUf("UP");

        when(eventRepository.findById("1")).thenReturn(Optional.of(existingEvent));
        when(viaCepClient.getCepDetails("01002000")).thenReturn(viaCepResponse);
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        EventDTO result = eventService.updateEvent("1", updateDTO);

        assertNotNull(result);
        assertEquals("Updated Event", result.getEventName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void softDeleteEvent_Success() {
        Event event = new Event();
        event.setId("1");
        event.setDeleted(false);

        when(eventRepository.findById("1")).thenReturn(Optional.of(event));

        boolean result = eventService.softDeleteEvent("1");

        assertTrue(result);
        assertTrue(event.isDeleted());
        verify(eventRepository, times(1)).save(event);
    }
}