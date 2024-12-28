package com.compassuol.ms_ticket_manager.dto;

import lombok.*;

@Getter @Setter
@Data
@Builder
public class TicketResponseDTO {

    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private EventDetailsDTO event;
    private String status;
    private Double brlTotalAmount;
    private Double usdTotalAmount;
}
