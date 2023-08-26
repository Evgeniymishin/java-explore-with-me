package ru.practicum.main.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.StatsClient;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationException;
import ru.practicum.main.request.repository.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PublicEventServiceImpl extends EventService implements PublicEventService {

    private final EventRepository eventRepository;

    @Autowired
    public PublicEventServiceImpl(EventRepository eventRepository, RequestRepository requestRepository, StatsClient stats) {
        super(requestRepository, stats);
        this.eventRepository = eventRepository;
    }

    public EventFullDto get(Long eventId, HttpServletRequest servletRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + eventId));
        if (event.getState() != Constant.State.PUBLISHED)
            throw new NotFoundException("Не найден пользователь с id = " + eventId);
        sendViews(servletRequest);
        Map<Long, Integer> confirmedRequests = getConfirmedRequests(List.of(event));
        Map<Long, Long> views = getStats(List.of(event));
        return EventMapper.toFullEventDto(event,
                views.get(event.getId()) != null ? views.get(event.getId()) : 0,
                confirmedRequests.get(event.getId()) != null ? confirmedRequests.get(event.getId()) : 0);
    }

    public List<EventFullDto> getAll(EventRequestByParams request, HttpServletRequest servletRequest) {
        if (request.getRangeStart() != null && request.getRangeEnd() != null && request.getRangeEnd().isBefore(request.getRangeStart())) {
            throw new ValidationException("Время окончания не может быть раньше времени старта.");
        }
        sendViews(servletRequest);
        List<Event> events = eventRepository.findAllByRequest(request);
        if (events.size() == 0) {
            return new ArrayList<>();
        }
        Map<Long, Integer> confirmedRequests = getConfirmedRequests(events);
        Map<Long, Long> views = getStats(events);
        return events.stream().map(event -> EventMapper.toFullEventDto(
                        event,
                        views.get(event.getId()) != null ? views.get(event.getId()) : 0,
                        confirmedRequests.get(event.getId()) != null ? confirmedRequests.get(event.getId()) : 0
                ))
                .sorted(request.getSort() != null && request.getSort().equals(Constant.UserRequestSort.VIEWS) ?
                        Comparator.comparingLong(EventFullDto::getViews) : EventFullDto::compareTo)
                .collect(Collectors.toList());
    }
}
