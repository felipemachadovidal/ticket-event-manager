package com.compassuol.ms_ticket_manager.client;

import com.compassuol.ms_ticket_manager.dto.EventResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-event-manager", url = "http://localhost:8082/eventmanagement/v1")
public interface EventManagerClient {
    @GetMapping("/get-event/{id}")
    EventResponseDTO getEventDetails(@PathVariable("id") String id);
}
