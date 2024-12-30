package com.compassuol.ms_event_manager.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-event-manager", url = "http://ms-event-manager:8082/eventmanagement/v1")
public interface EventClient {

}
