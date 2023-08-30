package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.event.repository.LocationRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationException;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repository.RequestRepository;
import ru.practicum.main.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        return EventMapper.toFullEventDto(eventRepository.save(EventMapper.toEvent(
                newEventDto,
                categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new NotFoundException("Не найдена категория с id = " + newEventDto.getCategory())),
                locationRepository.save(newEventDto.getLocation()),
                userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId)))));
    }

    public EventFullDto update(Long userId, Long eventId, UpdateEventRequest request) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ConflictException("Невозможно редактировать чужое событие.");
        if (event.getState().equals(Constant.State.PUBLISHED))
            throw new ConflictException("Невозможно редактировать опубликованное событие.");
        return EventMapper.toFullEventDto(eventRepository.save(EventMapper.toEventUpdated(event,
                request,
                request.getCategory() == null ? event.getCategory() : categoryRepository.findById(request.getCategory()).get(),
                request.getLocation() == null ? event.getLocation() : locationRepository.save(request.getLocation()))
        ));
    }

    public EventRequestStatusUpdateResult updateRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestsForUpdate) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ValidationException("Невозможно редактировать список участников чужого события.");
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            throw new ValidationException("Запись на это событие невозмонжа");
        if (requestsForUpdate.getStatus().equals(Constant.StateParticipation.CONFIRMED) &&
                requestRepository.findRequestsByEventIds(List.of(eventId)).size() + requestsForUpdate.getRequestIds().size() > event.getParticipantLimit()) {
            throw new ConflictException("Невозможно превысить лимит участников события.");
        }
        List<Request> requests = requestRepository.findAllById(requestsForUpdate.getRequestIds());
        if (requestsForUpdate.getStatus().equals(Constant.StateParticipation.CONFIRMED)) {
            requests.forEach(request -> request.setStatus(Constant.StateParticipation.CONFIRMED));
            requestRepository.saveAll(requests);
            return new EventRequestStatusUpdateResult(requests.stream()
                    .map(RequestMapper::toParticipationDto)
                    .collect(Collectors.toList()), new ArrayList<>());
        } else {
            requests.forEach(request -> {
                if (request.getStatus() == Constant.StateParticipation.CONFIRMED) {
                    throw new ConflictException("Нельзя отклонить уже подтвержденный запрос.");
                }
                request.setStatus(Constant.StateParticipation.REJECTED);
            });
            requestRepository.saveAll(requests);
            return new EventRequestStatusUpdateResult(new ArrayList<>(),
                    requests.stream()
                            .map(RequestMapper::toParticipationDto)
                            .collect(Collectors.toList()));
        }
    }

    @Transactional(readOnly = true)
    public EventFullDto get(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ValidationException("Невозможно редактировать чужое событие.");
        return EventMapper.toFullEventDto(event);
    }

    @Transactional(readOnly = true)
    public List<EventShortDto> getAll(Long userId, int from, int size) {
        return eventRepository.findEventsByInitiator(
                        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId)),
                        PageRequest.of(from / size, size))
                .stream().map(EventMapper::toShortEventDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAllParticipationsByEvent(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ValidationException("Невозможно просматривать участников чужого события.");
        return requestRepository.findAllEventRequestsByEvent(event).stream()
                .map(RequestMapper::toParticipationDto)
                .collect(Collectors.toList());
    }
}
