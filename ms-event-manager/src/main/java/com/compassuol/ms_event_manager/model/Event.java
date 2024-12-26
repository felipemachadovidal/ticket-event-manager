package com.compassuol.ms_event_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Builder @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Document(collection = "events")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String eventId;

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    private String cep;

    private String logradouro;
    private String bairro;



    private boolean deleted = false;

    public Event(String eventId, String eventName, LocalDateTime dateTime, String cep, String bairro, String logradouro) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.cep = cep;
        this.bairro = bairro;
        this.logradouro = logradouro;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", dateTime=" + dateTime +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", logradouro='" + logradouro + '\'' +
                '}';
    }}

