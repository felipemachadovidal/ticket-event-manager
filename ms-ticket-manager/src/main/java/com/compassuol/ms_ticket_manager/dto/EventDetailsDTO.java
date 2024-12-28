package com.compassuol.ms_ticket_manager.dto;
import com.compassuol.ms_event_manager.dto.*;



import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDetailsDTO {

    private String id;
    private String eventName;
    private String eventDateTime;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}
