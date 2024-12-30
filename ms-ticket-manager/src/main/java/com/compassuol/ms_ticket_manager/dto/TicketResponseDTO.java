package com.compassuol.ms_ticket_manager.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {
    private String ticketid;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventName;
    private String id;
    private String status;
    private Double brlAmount;
    private Double usdAmount;

    public TicketResponseDTO(String ticketid, String customerName, String cpf, String customerMail, String eventName, String status, Double brlAmount, Double usdAmount) {
    }
}