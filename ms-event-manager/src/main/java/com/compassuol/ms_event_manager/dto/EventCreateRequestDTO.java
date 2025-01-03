package com.compassuol.ms_event_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateRequestDTO {

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    private String cep;

    public @NotBlank String getCep() {
        return cep;
    }

    public void setCep(@NotBlank String cep) {
        this.cep = cep;
    }

    public @NotNull LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@NotNull LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public @NotBlank String getEventName() {
        return eventName;
    }

    public void setEventName(@NotBlank String eventName) {
        this.eventName = eventName;
    }
}