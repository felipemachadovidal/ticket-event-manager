package com.compassuol.ms_ticket_manager.controller;


import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketmanagement/v1")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
        try {
            TicketResponseDTO ticketResponse = ticketService.createTicket(ticketDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
