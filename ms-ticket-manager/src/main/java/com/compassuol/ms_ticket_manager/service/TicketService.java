package com.compassuol.ms_ticket_manager.service;

import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.EventResponseDTO;
import com.compassuol.ms_ticket_manager.dto.TicketDTO;
import com.compassuol.ms_ticket_manager.dto.TicketResponseDTO;
import com.compassuol.ms_ticket_manager.execeptions.EventNotFoundException;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import feign.FeignException;
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
        // Verifique se o ID do evento é válido
        System.out.println("Creating ticket for event ID: " + ticketDTO.getId());
        EventResponseDTO eventDetails;

        try {
            eventDetails = eventManagerClient.getEventDetails(ticketDTO.getId());
        } catch (FeignException.NotFound e) {
            throw new EventNotFoundException("Event not found with ID: " + ticketDTO.getId());
        }

        if (eventDetails == null) {
            throw new EventNotFoundException("Event not found with ID: " + ticketDTO.getId());
        }

        // Agora você pode continuar com a criação do ticket
        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setId(ticketDTO.getId());
        ticket.setEventName(eventDetails.getEventName());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());
        ticket.setStatus("concluído");

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketResponseDTO response = new TicketResponseDTO(
                savedTicket.getId(),
                savedTicket.getCustomerName(),
                savedTicket.getCpf(),
                savedTicket.getCustomerMail(),
                savedTicket.getEventName(),
                savedTicket.getStatus(),
                savedTicket.getBrlAmount(),
                savedTicket.getUsdAmount()
        );

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
        response.setTicketid(updatedTicket.getTicketid());
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

        Optional<Ticket> tickets = ticketRepository.findByCpf(cpf);

        return tickets.stream()
                .map(ticket -> {
                    TicketResponseDTO response = new TicketResponseDTO();
                    response.setTicketid(ticket.getTicketid());
                    response.setCustomerName(ticket.getCustomerName());
                    response.setCpf(ticket.getCpf());
                    response.setCustomerMail(ticket.getCustomerMail());
                    response.setEventName(ticket.getEventName());
                    response.setStatus(ticket.getStatus());
                    response.setBrlAmount(ticket.getBrlAmount());
                    response.setUsdAmount(ticket.getUsdAmount());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public TicketResponseDTO getTicketById(String ticketId) {
        // Buscar o ticket no banco de dados
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Retornar o TicketResponseDTO com as informações do ticket
        return new TicketResponseDTO(
                ticket.getTicketid(),
                ticket.getCustomerName(),
                ticket.getCpf(),
                ticket.getCustomerMail(),
                ticket.getEventName(),
                ticket.getStatus(),
                ticket.getBrlAmount(),
                ticket.getUsdAmount()
        );
    }

}
