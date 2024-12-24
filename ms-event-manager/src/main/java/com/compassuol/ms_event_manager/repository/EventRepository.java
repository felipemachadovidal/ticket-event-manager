package com.compassuol.ms_event_manager.repository;

import com.compassuol.ms_event_manager.model.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByDeletedFalse(Sort sort);
    Optional<Event> findById(Long id);
}
