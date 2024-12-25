package com.compassuol.ms_ticket_manager.dto;

import com.compassuol.ms_event_manager.dto.EventDTO;
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
    private String eventId;

    @NotNull
    private Integer tickets;

    private String status;

    private EventDTO eventDetails;
}
