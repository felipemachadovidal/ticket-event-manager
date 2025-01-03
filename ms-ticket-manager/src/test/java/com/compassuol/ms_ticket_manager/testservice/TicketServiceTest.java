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

import static org.junit.jupiter.api.Assertions.*;


class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Criação manual do EventManagerClient no lugar de usar mocks
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
                return null; // Se o ID não for "123", retorna null
            }
        };

        // Inicializa o TicketService com as dependências manualmente criadas
        ticketService = new TicketService(ticketRepository, eventManagerClient, rabbitTemplate);
    }

    @Test
    void testCreateTicket() {
        // Dados de entrada para o teste
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId("123");
        ticketDTO.setCustomerName("John Doe");
        ticketDTO.setCpf("12345678901");
        ticketDTO.setCustomerMail("john.doe@example.com");
        ticketDTO.setBrlAmount(100.0);
        ticketDTO.setUsdAmount(20.0);

        // Criando o Ticket que será salvo
        Ticket savedTicket = new Ticket();
        savedTicket.setTicketid(new ObjectId()); // Usando ObjectId
        savedTicket.setCustomerName(ticketDTO.getCustomerName());
        savedTicket.setCpf(ticketDTO.getCpf());
        savedTicket.setCustomerMail(ticketDTO.getCustomerMail());
        savedTicket.setEventName("Concert");
        savedTicket.setStatus("concluído");
        savedTicket.setBrlAmount(ticketDTO.getBrlAmount());
        savedTicket.setUsdAmount(ticketDTO.getUsdAmount());

        // Simulando o comportamento do ticketRepository para salvar o ticket
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(savedTicket);

        // Simulação para RabbitTemplate
        Mockito.doNothing().when(rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        // Chamada ao método do TicketService
        TicketResponseDTO response = ticketService.createTicket("123", ticketDTO);

        // Verificações
        assertNotNull(response);
        assertEquals(savedTicket.getTicketid().toString(), response.getTicketid().toString()); // Comparando ObjectId convertido para String
        assertEquals("John Doe", response.getCustomerName());
        assertEquals("Concert", response.getEventName());
        assertEquals("concluído", response.getStatus());

        // Verifique se o método de enviar para o RabbitMQ foi chamado
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        // Verifique se o método de salvar ticket foi chamado
        Mockito.verify(ticketRepository, Mockito.times(1)).save(Mockito.any(Ticket.class));
    }
}