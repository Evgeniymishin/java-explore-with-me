package ru.practicum.main.event.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.Constant;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final EntityManager manager;

    @Override
    public List<Event> findAllByRequest(EventRequestByParams request) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();
        if (request.getUsers() != null && request.getUsers().size() > 0) {
            criteria = builder.and(criteria, root.get("initiator").in(request.getUsers()));
        }
        if (request.getStates() != null && request.getStates().size() > 0) {
            criteria = builder.and(criteria, root.get("state").in(request.getStates()));
        }
        if (request.getText() != null) {
            criteria = builder.and(criteria, builder.or(builder.like(builder.lower(root.get("annotation")), "%" + request.getText().toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("description")), "%" + request.getText().toLowerCase() + "%")));
        }
        if (request.getCategories() != null && request.getCategories().size() > 0) {
            criteria = builder.and(criteria, root.get("category").in(request.getCategories()));
        }
        if (request.getPaid() != null && request.getPaid()) {
            criteria = builder.and(criteria, root.get("paid").in(request.getPaid()));
        }
        if (request.getOnlyAvailable() != null && request.getOnlyAvailable()) {
            criteria = builder.and(criteria, root.get("participantLimit").in(request.getOnlyAvailable()));
        }
        if (request.getRangeStart() != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(request.getRangeStart().format(Constant.FORMATTER), Constant.FORMATTER)));
        }
        if (request.getRangeEnd() != null) {
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class),
                    LocalDateTime.parse(request.getRangeEnd().format(Constant.FORMATTER), Constant.FORMATTER)));
        }
        query.select(root).where(criteria);
        return manager.createQuery(query).setFirstResult(request.getFrom()).setMaxResults(request.getSize()).getResultList();
    }
}
