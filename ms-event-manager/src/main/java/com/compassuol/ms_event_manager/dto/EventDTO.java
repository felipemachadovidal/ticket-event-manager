package com.compassuol.ms_event_manager.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EventDTO {

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    private String cep;



}
