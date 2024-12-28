package com.compassuol.ms_ticket_manager.service;



import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.dto.EventDetailsDTO;
import com.compassuol.ms_ticket_manager.dto.TicketDTO;
import com.compassuol.ms_ticket_manager.dto.TicketResponseDTO;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventManagerClient eventManagerClient;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventManagerClient eventManagerClient,ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.eventManagerClient = eventManagerClient;
        this.modelMapper = modelMapper;
    }

    public TicketResponseDTO createTicket(TicketDTO ticketDTO) {
        EventDetailsDTO event;


        try {
            event = eventManagerClient.getEventDetails(ticketDTO.getEventId());
        } catch (FeignException.NotFound ex) {
            throw new IllegalArgumentException("Event not found with ID: " + ticketDTO.getEventId(), ex);
        }

        Ticket ticket = Ticket.builder()
                .customerName(ticketDTO.getCustomerName())
                .cpf(ticketDTO.getCpf())
                .customerMail(ticketDTO.getCustomerMail())
                .eventId(event.getId()) // Consistente com o ID do evento
                .eventName(event.getEventName())
                .brlAmount(Double.valueOf(ticketDTO.getBrlAmount()))
                .usdAmount(Double.valueOf(ticketDTO.getUsdAmount()))
                .status("concluído")
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketResponseDTO.builder()
                .ticketId(savedTicket.getTicketid())
                .cpf(savedTicket.getCpf())
                .customerName(savedTicket.getCustomerName())
                .customerMail(savedTicket.getCustomerMail())
                .event(event)
                .brlTotalAmount(savedTicket.getBrlAmount())
                .usdTotalAmount(savedTicket.getUsdAmount())
                .status(savedTicket.getStatus())
                .build();
    }
}