package com.compassuol.ms_ticket_manager.client;

import com.compassuol.ms_ticket_manager.dto.EventDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eventManagerClient", url = "http://event-manager-service")
public interface EventManagerClient {

    @GetMapping("/eventmanagement/v1/get-event/{id}")
    EventDetailsDTO getEventDetails(@PathVariable("id") String eventId);
}
