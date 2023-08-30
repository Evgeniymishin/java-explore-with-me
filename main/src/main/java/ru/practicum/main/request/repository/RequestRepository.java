package ru.practicum.main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.Constant;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.model.ConfirmedRequests;
import ru.practicum.main.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findAllEventRequestsByEvent(Event event);

    Integer countEventRequestsByEventAndStatus(Event event, Constant.StateParticipation status);

    List<Request> findRequestsByRequesterId(Long requesterId);

    @Query(value = "SELECT event_id as eventId, count(requester_id) as count " +
            "FROM requests " +
            "WHERE event_id IN :eventIds AND status = 'CONFIRMED' " +
            "GROUP BY eventId",
            nativeQuery = true)
    List<ConfirmedRequests> findRequestsByEventIds(List<Long> eventIds);
}