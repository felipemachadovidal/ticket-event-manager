package com.compassuol.ms_event_manager.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-event-manager", url = "http://ms-event-manager:8080/api/v1/events")
public interface EventClient {
}
