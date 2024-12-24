package com.compassuol.ms_ticket_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketDTO {
    @NotBlank
    private String customerName;

    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String customerMail;

    @NotBlank
    private String eventId; // Referência ao ID do evento.

    @NotNull
    private Integer tickets;

    private String status;

    private EventDTO eventDetails;
}
