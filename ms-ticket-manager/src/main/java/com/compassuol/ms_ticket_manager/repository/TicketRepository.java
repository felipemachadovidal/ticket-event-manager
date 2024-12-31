package com.compassuol.ms_ticket_manager.repository;

import com.compassuol.ms_ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCpf(String cpf);
    Optional<Ticket> findByTicketid(String ticketid);
}
