package com.compassuol.ms_ticket_manager.service;

import com.compassuol.ms_ticket_manager.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.EventResponseDTO;
import com.compassuol.ms_ticket_manager.dto.TicketDTO;
import com.compassuol.ms_ticket_manager.dto.TicketResponseDTO;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        EventResponseDTO eventResponse = eventManagerClient.getEventDetails(id);

        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventName(eventResponse.getEventName());  // Usando o nome do evento
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(savedTicket.getTicketid(), savedTicket.getCustomerName(),
                savedTicket.getCpf(), savedTicket.getCustomerMail(),
                savedTicket.getEventName(), savedTicket.getStatus(),
                savedTicket.getBrlAmount(), savedTicket.getUsdAmount());
        return ticketResponseDTO;
    }

    public TicketResponseDTO getTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

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
}
