package com.compassuol.ms_ticket_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter @Setter
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    int ticketnumber;



}
