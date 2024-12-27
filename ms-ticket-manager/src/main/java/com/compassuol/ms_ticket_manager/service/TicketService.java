package com.compassuol.ms_ticket_manager.service;


import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.TicketResponseDTO;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
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

    public Ticket createTicket(TicketResponseDTO ticketResponseDTO) {
        // Verificar se o evento existe no Event Service
        boolean eventExists = eventClient.checkEventExists(TicketResponseDTO.getEventId());

        if (!eventExists) {
            throw new IllegalArgumentException("Event does not exist!");
        }

        Ticket ticket = new Ticket();
        ticket.setEventId(TicketResponseDTO.getEventId());
        ticket.setEventName(TicketResponseDTO.ge;
        ticket.setBrlAmount(TicketResponseDTO.ge);

        return ticketRepository.save(ticket);
    }


}
