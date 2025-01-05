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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);


        Mockito.doNothing().when(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        TicketResponseDTO response = ticketService.createTicket("123", ticketDTO);


        assertNotNull(response);
        assertEquals(savedTicket.getTicketid().toString(), response.getTicketid().toString());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("Concert", response.getEventName());
        assertEquals("concluído", response.getStatus());


        verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        verify(ticketRepository, Mockito.times(1)).save(any(Ticket.class));
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

    @Test
    void testListTicketsByCpf() {

        String cpf = "12345678901";

        Ticket ticket1 = new Ticket();
        ticket1.setTicketid(new ObjectId("6452d37fb1742e1a60d5f9d1"));
        ticket1.setCustomerName("John Doe");
        ticket1.setEventName("Concert");

        Ticket ticket2 = new Ticket();
        ticket2.setTicketid(new ObjectId("6452d37fb1742e1a60d5f9d2"));
        ticket2.setCustomerName("Jane Doe");
        ticket2.setEventName("Event");

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketRepository.findByCpf(cpf)).thenReturn(tickets);

        List<Ticket> response = ticketService.listTicketsByCpf(cpf);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("John Doe", response.get(0).getCustomerName());
        assertEquals("Jane Doe", response.get(1).getCustomerName());

        verify(ticketRepository).findByCpf(cpf);
    }

    @Test
    void testCancelTicket() {

        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");

        Ticket ticket = new Ticket();
        ticket.setTicketid(ticketId);
        ticket.setCustomerName("John Doe");
        ticket.setEventName("Concert");
        ticket.setStatus("concluído");

        when(ticketRepository.findById(String.valueOf(ticketId))).thenReturn(Optional.of(ticket));

        ticketService.cancelTicket(ticketId);

        assertEquals("CANCELLED", ticket.getStatus());

        verify(ticketRepository).findById(String.valueOf(ticketId));
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testUpdateTicket() {
        // Criando um ObjectId simulado
        ObjectId ticketId = new ObjectId("6452d37fb1742e1a60d5f9d1");

        // Criando o Ticket DTO para atualização
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("Jane Doe");
        ticketDTO.setCpf("98765432100");
        ticketDTO.setCustomerMail("jane.doe@example.com");
        ticketDTO.setEventName("New Concert");
        ticketDTO.setStatus("updated");
        ticketDTO.setBrlAmount(150.0);
        ticketDTO.setUsdAmount(30.0);

        // Criando o ticket simulado a ser retornado pelo repositório
        Ticket ticket = new Ticket();
        ticket.setTicketid(ticketId);
        ticket.setCustomerName("John Doe");
        ticket.setCpf("12345678901");
        ticket.setCustomerMail("john.doe@example.com");
        ticket.setEventName("Concert");
        ticket.setStatus("concluído");
        ticket.setBrlAmount(100.0);
        ticket.setUsdAmount(20.0);

        when(ticketRepository.findById(String.valueOf(ticketId))).thenReturn(Optional.of(ticket));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);


        TicketResponseDTO response = ticketService.updateTicket(ticketId, ticketDTO);


        assertNotNull(response);
        assertEquals("Jane Doe", response.getCustomerName());
        assertEquals("New Concert", response.getEventName());
        assertEquals("updated", response.getStatus());
        assertEquals(150.0, response.getBrlAmount());
        assertEquals(30.0, response.getUsdAmount());

        verify(ticketRepository).findById(String.valueOf(ticketId));
        verify(ticketRepository).save(any(Ticket.class));
    }

}
