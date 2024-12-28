package com.compassuol.ms_event_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.persistence.PrePersist;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Builder @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Document(collection = "events")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotBlank
    private String eventName;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    private String cep;

    private String logradouro;
    private String bairro;

    private String uf;

    private String cidade;



    private boolean deleted = false;

    public Event(String id, String eventName, LocalDateTime dateTime, String cep, String bairro, String logradouro) {
        this.id = id;
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

