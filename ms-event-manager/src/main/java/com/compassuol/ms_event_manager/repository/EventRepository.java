package com.compassuol.ms_event_manager.repository;

import com.compassuol.ms_event_manager.model.Event;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByDeletedFalse(Sort sort);
    @Query("{ '_id': ?0 }")
    Optional<Event> findByObjectId(ObjectId id);
}
