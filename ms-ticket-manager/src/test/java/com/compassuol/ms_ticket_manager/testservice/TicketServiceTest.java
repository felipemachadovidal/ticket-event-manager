package com.compassuol.ms_ticket_manager.testservice;


import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import com.compassuol.ms_ticket_manager.service.TicketService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;


class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        EventManagerClient eventManagerClient = new EventManagerClient() {
            @Override
            public EventResponseDTO getEventDetails(String id) {
                // Criamos um evento simulado com o id "123"
                if ("123".equals(id)) {
                    EventResponseDTO eventResponseDTO = new EventResponseDTO();
                    eventResponseDTO.setId("123");
                    eventResponseDTO.setEventName("Concert");
                    eventResponseDTO.setEventDateTime(LocalDateTime.now());
                    eventResponseDTO.setLogradouro("Street ABC");
                    eventResponseDTO.setBairro("Downtown");
                    eventResponseDTO.setLocalidade("City");
                    eventResponseDTO.setUf("State");
                    return eventResponseDTO;
                }
                return null;
            }
        };

        ticketService = new TicketService(ticketRepository, eventManagerClient, rabbitTemplate);
    }

    @Test
    void testCreateTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId("123");
        ticketDTO.setCustomerName("John Doe");
        ticketDTO.setCpf("12345678901");
        ticketDTO.setCustomerMail("john.doe@example.com");
        ticketDTO.setBrlAmount(100.0);
        ticketDTO.setUsdAmount(20.0);

        Ticket savedTicket = new Ticket();
        savedTicket.setTicketid(new ObjectId());
        savedTicket.setCustomerName(ticketDTO.getCustomerName());
        savedTicket.setCpf(ticketDTO.getCpf());
        savedTicket.setCustomerMail(ticketDTO.getCustomerMail());
        savedTicket.setEventName("Concert");
        savedTicket.setStatus("concluído");
        savedTicket.setBrlAmount(ticketDTO.getBrlAmount());
        savedTicket.setUsdAmount(ticketDTO.getUsdAmount());

        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(savedTicket);


        Mockito.doNothing().when(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        TicketResponseDTO response = ticketService.createTicket("123", ticketDTO);


        assertNotNull(response);
        assertEquals(savedTicket.getTicketid().toString(), response.getTicketid().toString());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("Concert", response.getEventName());
        assertEquals("concluído", response.getStatus());


        verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        verify(ticketRepository, Mockito.times(1)).save(Mockito.any(Ticket.class));
    }
    @Test
    void testGetTicketById() {
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");

        Ticket ticket = new Ticket();
        ticket.setTicketid(ticketId);
        ticket.setCustomerName("John Doe");
        ticket.setEventName("Concert");

        when(ticketRepository.findByObjectId(ticketId)).thenReturn(Optional.of(ticket));

        Ticket response = ticketService.getTicketById(ticketId);

        assertNotNull(response);
        assertEquals(ticket.getTicketid(), response.getTicketid());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("Concert", response.getEventName());

        verify(ticketRepository).findByObjectId(ticketId);
    }

}
