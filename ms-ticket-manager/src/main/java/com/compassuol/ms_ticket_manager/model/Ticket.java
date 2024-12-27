package com.compassuol.ms_ticket_manager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@NoArgsConstructor @AllArgsConstructor
@Builder @Getter @Setter
@Document(collection = "tickets")
public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotBlank
    private String customerName;

    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String customerMail;

    @NotBlank
    private String eventName;

    @NotBlank
    private String eventId;

    @NotNull
    private Integer tickets;

    @NotBlank
    private String status;



}
