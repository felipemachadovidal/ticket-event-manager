package com.compassuol.ms_ticket_manager.model;

import com.compassuol.ms_ticket_manager.dto.TicketDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "tickets")
public class Ticket {

    @Id
    @Field("_id")
    private ObjectId ticketid;

    private String customerName;

    private String cpf;

    private String customerMail;


    private String eventName;


    private String id;


    private String status;

    private Double brlAmount;

    private Double usdAmount;

    public Double getBrlAmount() {
        return brlAmount;
    }

    public void setBrlAmount(Double brlAmount) {
        this.brlAmount = brlAmount;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObjectId getTicketid() {
        return ticketid;
    }

    public void setTicketid(ObjectId ticketid) {
        this.ticketid = ticketid;
    }

    public Double getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(Double usdAmount) {
        this.usdAmount = usdAmount;
    }
    public Ticket(TicketDTO ticketDTO) {
        this.customerName = ticketDTO.getCustomerName();
        this.cpf = ticketDTO.getCpf();
        this.customerMail = ticketDTO.getCustomerMail();
        this.id = ticketDTO.getId();
        this.eventName = ticketDTO.getEventName();
        this.brlAmount = ticketDTO.getBrlAmount();
        this.usdAmount = ticketDTO.getUsdAmount();
    }

    public Ticket(){

    }
}
