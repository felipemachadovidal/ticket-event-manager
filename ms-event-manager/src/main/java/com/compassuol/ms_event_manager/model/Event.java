package com.compassuol.ms_event_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor @Getter @Setter @NoArgsConstructor
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

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", dateTime=" + dateTime +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", logradouro='" + logradouro + '\'' +
                '}';
    }


}
