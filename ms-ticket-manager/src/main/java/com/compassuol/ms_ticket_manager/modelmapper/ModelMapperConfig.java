package com.compassuol.ms_ticket_manager.modelmapper;

import com.compassuol.ms_event_manager.model.Event;
import com.compassuol.ms_ticket_manager.dto.EventDetailsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Event.class, EventDetailsDTO.class)
                .addMapping(Event::getId, EventDetailsDTO::setId)
                .addMapping(Event::getEventName, EventDetailsDTO::setEventName)
                .addMapping(Event::getDateTime, EventDetailsDTO::setEventDateTime)
                .addMapping(Event::getLogradouro, EventDetailsDTO::setLogradouro)
                .addMapping(Event::getBairro, EventDetailsDTO::setBairro)
                .addMapping(Event::getCidade, EventDetailsDTO::setCidade)
                .addMapping(Event::getUf, EventDetailsDTO::setUf);

        return modelMapper;
    }
}
