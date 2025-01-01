package com.compassuol.ms_ticket_manager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter @Setter
public class TicketResponseDTO {
    private ObjectId ticketid;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventName;
    private String id;
    private String status;
    private Double brlAmount;
    private Double usdAmount;
    private EventResponseDTO event;
    private EventDetails eventDetails;

    public TicketResponseDTO() {
        
    }

    public TicketResponseDTO(ObjectId ticketid, String customerName, String cpf, String customerMail, String eventName, String status, Double brlAmount, Double usdAmount) {
    }

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

    public EventResponseDTO getEvent() {
        return event;
    }

    public void setEvent(EventResponseDTO event) {
        this.event = event;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
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


    public static class EventDetails {
        private String id;
        private String eventName;
        private LocalDateTime eventDateTime;
        private String logradouro;
        private String bairro;
        private String cidade;
        private String uf;

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public LocalDateTime getEventDateTime() {
            return eventDateTime;
        }

        public void setEventDateTime(LocalDateTime eventDateTime) {
            this.eventDateTime = eventDateTime;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}