package com.compassuol.ms_ticket_manager.service;

import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.EventResponseDTO;
import com.compassuol.ms_ticket_manager.dto.TicketDTO;
import com.compassuol.ms_ticket_manager.dto.TicketResponseDTO;
import com.compassuol.ms_ticket_manager.execeptions.EventNotFoundException;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import feign.FeignException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventManagerClient eventManagerClient;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventManagerClient eventManagerClient) {
        this.ticketRepository = ticketRepository;
        this.eventManagerClient = eventManagerClient;
    }

    public TicketResponseDTO createTicket(String id, TicketDTO ticketDTO) {
        System.out.println("Creating ticket for event ID: " + ticketDTO.getId());

        // Verificar detalhes do evento
        EventResponseDTO eventDetails = eventManagerClient.getEventDetails(ticketDTO.getId());
        if (eventDetails == null) {
            throw new IllegalArgumentException("Event not found with ID: " + ticketDTO.getId());
        }

        // Criar e preencher a entidade Ticket
        Ticket ticket = new Ticket();
        ticket.setTicketid(null); // Deixe nulo para que o MongoDB gere o _id
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventName(eventDetails.getEventName());
        ticket.setStatus("concluído");
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        // Salvar o ticket no banco
        Ticket savedTicket = ticketRepository.save(ticket);

        // Construir o objeto de resposta
        TicketResponseDTO response = new TicketResponseDTO();
        response.setTicketid(String.valueOf(savedTicket.getTicketid()));
        response.setCustomerName(savedTicket.getCustomerName());
        response.setCpf(savedTicket.getCpf());
        response.setCustomerMail(savedTicket.getCustomerMail());
        response.setEventName(savedTicket.getEventName());
        response.setStatus(savedTicket.getStatus());
        response.setBrlAmount(savedTicket.getBrlAmount());
        response.setUsdAmount(savedTicket.getUsdAmount());

        // Configurar os detalhes do evento no DTO
        TicketResponseDTO.EventDetails event = new TicketResponseDTO.EventDetails();
        event.setId(eventDetails.getId());
        event.setEventName(eventDetails.getEventName());
        event.setEventDateTime(eventDetails.getEventDateTime());
        event.setLogradouro(eventDetails.getLogradouro());
        event.setBairro(eventDetails.getBairro());
        event.setCidade(eventDetails.getLocalidade());
        event.setUf(eventDetails.getUf());
        response.setEventDetails(event); // Definir os detalhes do evento no DTO

        System.out.println("Ticket created: " + response);
        return response;
    }


    public TicketResponseDTO updateTicket(String ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventName(ticketDTO.getEventName());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        Ticket updatedTicket = ticketRepository.save(ticket);

        TicketResponseDTO response = new TicketResponseDTO();
        response.setTicketid(String.valueOf(updatedTicket.getTicketid()));
        response.setCustomerName(updatedTicket.getCustomerName());
        response.setCpf(updatedTicket.getCpf());
        response.setCustomerMail(updatedTicket.getCustomerMail());
        response.setEventName(updatedTicket.getEventName());
        response.setStatus(updatedTicket.getStatus());
        response.setBrlAmount(updatedTicket.getBrlAmount());
        response.setUsdAmount(updatedTicket.getUsdAmount());

        return response;
    }

    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        ticket.setStatus("CANCELLED");
        ticketRepository.save(ticket);
    }


    public List<TicketResponseDTO> listTicketsByCpf(String cpf) {
        // Buscar os tickets no banco de dados
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);

        // Transformar em DTOs
        return tickets.stream()
                .map(ticket -> new TicketResponseDTO(
                        ticket.getTicketid(),
                        ticket.getCustomerName(),
                        ticket.getCpf(),
                        ticket.getCustomerMail(),
                        ticket.getEventName(),
                        ticket.getStatus(),
                        ticket.getBrlAmount(),
                        ticket.getUsdAmount()
                ))
                .collect(Collectors.toList());
    }

    public Ticket getTicketById(ObjectId  ticketId) {
        try {
            // Usa o método personalizado para buscar no MongoDB
            return ticketRepository.findByObjectId(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found for ID: " + ticketId));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid ticket ID format: " + ticketId, e);
        }
    }

}
