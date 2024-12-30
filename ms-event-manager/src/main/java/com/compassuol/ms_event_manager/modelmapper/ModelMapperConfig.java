package com.compassuol.ms_event_manager.modelmapper;

import com.compassuol.ms_event_manager.dto.EventCreateRequestDTO;
import com.compassuol.ms_event_manager.model.Event;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(EventCreateRequestDTO.class, Event.class).addMappings(mapper -> {
            mapper.skip(Event::setId);
        });

        return modelMapper;
}}

