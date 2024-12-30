package com.compassuol.ms_ticket_manager.controller;


import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ticketmanagement/v1")
public class TicketController {


    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create/{eventId}")
    public TicketResponseDTO createTicket(@PathVariable String eventId, @RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(eventId, ticketDTO);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable("id") String ticketId) {
        TicketResponseDTO ticket = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable("id") String ticketId,
                                                          @RequestBody TicketDTO ticketDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicket(ticketId, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }
}
