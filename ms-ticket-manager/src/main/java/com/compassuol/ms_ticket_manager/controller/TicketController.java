package com.compassuol.ms_ticket_manager.controller;


import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/get-ticket/{ticketId}")
    public TicketResponseDTO getTicketById(@PathVariable String ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping("/list-tickets-by-cpf/{cpf}")
    public List<TicketResponseDTO> listTicketsByCpf(@PathVariable String cpf) {
        return ticketService.listTicketsByCpf(cpf);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable("id") String ticketId,
                                                          @RequestBody TicketDTO ticketDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicket(ticketId, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable("id") String ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após a exclusão
    }



}
