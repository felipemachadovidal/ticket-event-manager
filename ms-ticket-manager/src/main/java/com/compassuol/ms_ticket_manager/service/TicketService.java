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

        EventResponseDTO eventDetails = eventManagerClient.getEventDetails(ticketDTO.getId());
        if (eventDetails == null) {
            throw new IllegalArgumentException("Event not found with ID: " + ticketDTO.getId());
        }

        Ticket ticket = new Ticket();
        ticket.setTicketid(null);
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventName(eventDetails.getEventName());
        ticket.setStatus("concluído");
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketResponseDTO response = new TicketResponseDTO();
        response.setTicketid((savedTicket.getTicketid()));
        response.setCustomerName(savedTicket.getCustomerName());
        response.setCpf(savedTicket.getCpf());
        response.setCustomerMail(savedTicket.getCustomerMail());
        response.setEventName(savedTicket.getEventName());
        response.setStatus(savedTicket.getStatus());
        response.setBrlAmount(savedTicket.getBrlAmount());
        response.setUsdAmount(savedTicket.getUsdAmount());

        TicketResponseDTO.EventDetails event = new TicketResponseDTO.EventDetails();
        event.setId(eventDetails.getId());
        event.setEventName(eventDetails.getEventName());
        event.setEventDateTime(eventDetails.getEventDateTime());
        event.setLogradouro(eventDetails.getLogradouro());
        event.setBairro(eventDetails.getBairro());
        event.setCidade(eventDetails.getLocalidade());
        event.setUf(eventDetails.getUf());
        response.setEventDetails(event);

        System.out.println("Ticket created: " + response);
        return response;
    }


    public TicketResponseDTO updateTicket(ObjectId  ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(String.valueOf(ticketId))
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
        response.setTicketid((updatedTicket.getTicketid()));
        response.setCustomerName(updatedTicket.getCustomerName());
        response.setCpf(updatedTicket.getCpf());
        response.setCustomerMail(updatedTicket.getCustomerMail());
        response.setEventName(updatedTicket.getEventName());
        response.setStatus(updatedTicket.getStatus());
        response.setBrlAmount(updatedTicket.getBrlAmount());
        response.setUsdAmount(updatedTicket.getUsdAmount());

        return response;
    }

    public void cancelTicket(ObjectId  ticketId) {
        Ticket ticket = ticketRepository.findById(String.valueOf(ticketId))
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        ticket.setStatus("CANCELLED");
        ticketRepository.save(ticket);
    }


    public List<Ticket> listTicketsByCpf(String cpf) {
        // Exibe o CPF que está sendo consultado
        System.out.println("Buscando tickets para o CPF: " + cpf);

        // Consulta os tickets no banco de dados usando o repositório
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);

        // Verifica se encontrou tickets e imprime no log
        if (tickets.isEmpty()) {
            System.out.println("Nenhum ticket encontrado para o CPF: " + cpf);
        } else {
            System.out.println("Tickets encontrados: ");
            tickets.forEach(ticket -> System.out.println(ticket)); // Exibe os tickets no log
        }

        // Retorna a lista de tickets
        return tickets;
    }


    public Ticket getTicketById(ObjectId  ticketId) {
        try {
            return ticketRepository.findByObjectId(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found for ID: " + ticketId));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid ticket ID format: " + ticketId, e);
        }
    }

}
