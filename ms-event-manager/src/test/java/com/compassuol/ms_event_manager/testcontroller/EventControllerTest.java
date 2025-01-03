package com.compassuol.ms_event_manager.testcontroller;

import com.compassuol.ms_event_manager.controller.EventController;
import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
import com.compassuol.ms_event_manager.dto.EventDTO;
import com.compassuol.ms_event_manager.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Suporte para LocalDateTime
    }

    @Test
    void createEvent_Success() throws Exception {
        EventCreateRequestDTO requestDTO = new EventCreateRequestDTO("Sample Event", LocalDateTime.now(), "12345678");
        EventDTO responseDTO = EventDTO.builder()
                .id("676ee375a48b511225981d04")
                .eventName("Sample Event")
                .eventDateTime(requestDTO.getDateTime())
                .cep("12345678")
                .logradouro("Rua Exemplo")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .deleted(false)
                .build();

        when(eventService.createEvent(any(EventCreateRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/eventmanagement/v1/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.eventName").value(responseDTO.getEventName()))
                .andExpect(jsonPath("$.cep").value(responseDTO.getCep()))
                .andExpect(jsonPath("$.logradouro").value(responseDTO.getLogradouro()))
                .andExpect(jsonPath("$.bairro").value(responseDTO.getBairro()))
                .andExpect(jsonPath("$.localidade").value(responseDTO.getLocalidade()))
                .andExpect(jsonPath("$.uf").value(responseDTO.getUf()))
                .andExpect(jsonPath("$.deleted").value(false));
    }

    @Test
    void getAllEvents_Success() throws Exception {
        List<EventDTO> events = List.of(
                EventDTO.builder()
                        .id("1")
                        .eventName("Event 1")
                        .eventDateTime(LocalDateTime.now())
                        .cep("11111111")
                        .logradouro("Rua A")
                        .bairro("Bairro A")
                        .localidade("Cidade A")
                        .uf("AA")
                        .deleted(false)
                        .build(),
                EventDTO.builder()
                        .id("2")
                        .eventName("Event 2")
                        .eventDateTime(LocalDateTime.now())
                        .cep("22222222")
                        .logradouro("Rua B")
                        .bairro("Bairro B")
                        .localidade("Cidade B")
                        .uf("BB")
                        .deleted(false)
                        .build()
        );

        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/eventmanagement/v1/get-event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].logradouro").value("Rua A"))
                .andExpect(jsonPath("$[1].cep").value("22222222"));
    }

    @Test
    void findByEventId_Success() throws Exception {
        EventDTO eventDTO = EventDTO.builder()
                .id("676ee375a48b511225981d04")
                .eventName("Sample Event")
                .eventDateTime(LocalDateTime.now())
                .cep("12345678")
                .logradouro("Rua Exemplo")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .deleted(false)
                .build();

        when(eventService.findByEventId("676ee375a48b511225981d04")).thenReturn(Optional.of(eventDTO));

        mockMvc.perform(get("/eventmanagement/v1/get-event/676ee375a48b511225981d04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventDTO.getId()))
                .andExpect(jsonPath("$.logradouro").value(eventDTO.getLogradouro()));
    }

    @Test
    void updateEvent_Success() throws Exception {
        EventDTO requestDTO = EventDTO.builder()
                .eventName("Updated Event")
                .eventDateTime(LocalDateTime.now())
                .cep("12345678")
                .logradouro("Rua Atualizada")
                .bairro("Bairro Atualizado")
                .localidade("Cidade Atualizada")
                .uf("AT")
                .deleted(false)
                .build();

        EventDTO responseDTO = EventDTO.builder()
                .id("676ee375a48b511225981d04")
                .eventName("Updated Event")
                .eventDateTime(requestDTO.getEventDateTime())
                .cep(requestDTO.getCep())
                .logradouro(requestDTO.getLogradouro())
                .bairro(requestDTO.getBairro())
                .localidade(requestDTO.getLocalidade())
                .uf(requestDTO.getUf())
                .deleted(false)
                .build();

        when(eventService.updateEvent(eq("676ee375a48b511225981d04"), any(EventDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/eventmanagement/v1/update-event/676ee375a48b511225981d04")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.logradouro").value(responseDTO.getLogradouro()));
    }

    @Test
    void deleteEvent_Success() throws Exception {
        when(eventService.softDeleteEvent("676ee375a48b511225981d04")).thenReturn(true);

        mockMvc.perform(delete("/eventmanagement/v1/delete-event/676ee375a48b511225981d04"))
                .andExpect(status().isNoContent());
    }
}