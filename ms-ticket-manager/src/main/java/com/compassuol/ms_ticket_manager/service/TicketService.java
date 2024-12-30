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

        // Obter detalhes do evento
        EventResponseDTO eventResponse = eventManagerClient.getEventDetails(id);

        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventName(eventResponse.getEventName());  // Usando o nome do evento
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setBrlAmount(ticketDTO.getBrlAmount());
        ticket.setUsdAmount(ticketDTO.getUsdAmount());

        // Salvar no repositório
        Ticket savedTicket = ticketRepository.save(ticket);

        // Retornar a resposta
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(savedTicket.getTicketid(), savedTicket.getCustomerName(),
                savedTicket.getCpf(), savedTicket.getCustomerMail(),
                savedTicket.getEventName(), savedTicket.getStatus(),
                savedTicket.getBrlAmount(), savedTicket.getUsdAmount());
        return ticketResponseDTO;
    }
}
