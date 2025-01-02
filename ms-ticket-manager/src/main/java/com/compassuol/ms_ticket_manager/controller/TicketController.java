package com.compassuol.ms_ticket_manager.controller;


import com.compassuol.ms_ticket_manager.dto.*;
import com.compassuol.ms_ticket_manager.model.Ticket;
import com.compassuol.ms_ticket_manager.service.TicketService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create-ticket")
    public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO) {
        try {
            TicketResponseDTO ticketResponse = ticketService.createTicket(ticketDTO.getId(), ticketDTO);
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating ticket: " + e.getMessage());
        }
    }

    @GetMapping("/get-ticket/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable ObjectId ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/list-tickets-by-cpf/{cpf}")
    public List<Ticket> listTicketsByCpf(@PathVariable String cpf) {
        return ticketService.listTicketsByCpf(cpf);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable("id") ObjectId ticketId,
                                                          @RequestBody TicketDTO ticketDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicket(ticketId, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable("id") ObjectId ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }





}
