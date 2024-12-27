package com.compassuol.ms_ticket_manager.model;

import lombok.*;

@Data
public class EventDetailsDTO {

    private String eventId;
    private String eventName;
    private String eventDateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}
