package com.compassuol.ms_ticket_manager.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TicketDTO {

    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private Double brlAmount;
    private Double usdAmount;
}
