package ru.practicum.main.request.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.repository.RequestRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (event.getInitiator().getId().equals(userId))
            throw new ValidationException("Нельзя отправить запрос на свое событие.");
        if (!event.getState().equals(Constant.State.PUBLISHED)) {
            throw new ValidationException("Нельзя отправить запрос на участие в неподтвержденном событии.");
        }
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new ValidationException("Нельзя отправить запрос повторно.");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() ==
                requestRepository.findAllEventRequestsByEventAndStatus(event, Constant.StateParticipation.CONFIRMED).size()) {
            throw new ValidationException("Нет доступных мест для участия в этом событии");
        }
        Request request = Request
                .builder()
                .event(event)
                .requester(user)
                .status(!event.isRequestModeration() || event.getParticipantLimit() == 0 ? Constant.StateParticipation.CONFIRMED : Constant.StateParticipation.PENDING)
                .created(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER))
                .build();
        return RequestMapper.toParticipationDto(requestRepository.save(request));
    }

    public ParticipationRequestDto update(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Не найден запрос с id = " + requestId));
        if (!request.getRequester().getId().equals(userId))
            throw new NotFoundException("Не найден запрос с id = " + requestId);
        request.setStatus(Constant.StateParticipation.CANCELED);
        return RequestMapper.toParticipationDto(requestRepository.save(request));
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAll(Long userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Не найден пользователь с id = " + userId);
        return requestRepository.findRequestsByRequesterId(userId).stream()
                .map(RequestMapper::toParticipationDto)
                .collect(Collectors.toList());
    }
}
