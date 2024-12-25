package com.compassuol.ms_event_manager.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EventDTO {

    @NotNull
    private Long eventId;

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime eventDateTime;

    @NotBlank
    private String cep;

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotBlank
    private String cidade;

    @NotBlank
    private String uf;

    private boolean deleted;


}
