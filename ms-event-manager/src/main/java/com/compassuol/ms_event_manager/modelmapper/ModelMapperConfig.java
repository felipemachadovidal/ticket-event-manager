package com.compassuol.ms_event_manager.modelmapper;

import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
import com.compassuol.ms_event_manager.model.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public org.modelmapper.ModelMapper modelMapper() {
        org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.typeMap(EventCreateRequestDTO.class, Event.class).addMappings(mapper -> {
            mapper.skip(Event::setEventId);
        });

        return modelMapper;
    }
}

