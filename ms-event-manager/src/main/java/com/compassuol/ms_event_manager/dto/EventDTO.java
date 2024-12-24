package com.compassuol.ms_event_manager.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class EventDTO {

    @NotBlank
    private String eventId;

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime eventDateTime;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String uf;


}
