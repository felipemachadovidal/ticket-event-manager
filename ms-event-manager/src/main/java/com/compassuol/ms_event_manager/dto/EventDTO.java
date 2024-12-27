package com.compassuol.ms_event_manager.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EventDTO {

    private String id;

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
    private String localidade;

    @NotBlank
    private String uf;

    private boolean deleted;


}
