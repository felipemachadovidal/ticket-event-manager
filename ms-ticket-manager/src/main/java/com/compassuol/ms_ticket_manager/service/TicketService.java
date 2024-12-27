package com.compassuol.ms_ticket_manager.service;


import com.compassuol.ms_ticket_manager.client.EventManagerClient;
import com.compassuol.ms_ticket_manager.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventManagerClient eventManagerClient;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventManagerClient eventManagerClient, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.eventManagerClient = eventManagerClient;
        this.modelMapper = modelMapper;
    }
}
