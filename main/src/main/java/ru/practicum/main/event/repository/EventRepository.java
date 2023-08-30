package ru.practicum.main.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {
    List<Event> findEventsByCategoryId(Long categoryId);

    Page<Event> findEventsByInitiator(User user, Pageable pageable);
}
