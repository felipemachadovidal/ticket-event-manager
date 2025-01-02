package com.compassuol.ms_ticket_manager.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "ticketQueue";
    public static final String EXCHANGE_NAME = "ticketExchange";
    public static final String ROUTING_KEY = "ticketRoutingKey";

}
