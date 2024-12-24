package com.compassuol.ms_ticket_manager.repository;

import com.compassuol.ms_ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends MongoRepository<Ticket,String > {
}
