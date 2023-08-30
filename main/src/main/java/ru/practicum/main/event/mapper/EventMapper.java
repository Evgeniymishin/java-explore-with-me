package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Constant;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.Location;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public static Event toEvent(NewEventDto newEventDto, Category category, Location location, User initiator) {
        return Event
                .builder()
                .category(category)
                .location(location)
                .initiator(initiator)
                .participantLimit(newEventDto.getParticipantLimit())
                .paid(newEventDto.isPaid())
                .requestModeration(newEventDto.isRequestModeration())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .state(Constant.State.PENDING)
                .title(newEventDto.getTitle())
                .createdOn(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER))
                .eventDate(newEventDto.getEventDate())
                .build();
    }

    public static EventFullDto toFullEventDto(Event event) {
        return EventFullDto
                .builder()
                .id(event.getId())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .location(event.getLocation())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .confirmedRequests(0)
                .views(0)
                .participantLimit(event.getParticipantLimit())
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .state(event.getState())
                .title(event.getTitle())
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .build();
    }

    public static EventFullDto toFullEventDto(Event event, long views, long confirmedRequests) {
        return EventFullDto
                .builder()
                .id(event.getId())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .location(event.getLocation())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .confirmedRequests(confirmedRequests)
                .views(views)
                .participantLimit(event.getParticipantLimit())
                .paid(event.isPaid())
                .requestModeration(event.isRequestModeration())
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .state(event.getState())
                .title(event.getTitle())
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .publishedOn(event.getPublishedOn())
                .build();
    }

    public static EventShortDto toShortEventDto(Event event) {
        return EventShortDto
                .builder()
                .id(event.getId())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .build();

    }

    public static Event toEventUpdated(Event event, UpdateEventRequest request, Category category, Location location) {
        return Event
                .builder()
                .id(event.getId())
                .category(category)
                .location(location)
                .initiator(event.getInitiator())
                .participantLimit(request.getParticipantLimit() == null ? event.getParticipantLimit() : request.getParticipantLimit())
                .paid(request.getPaid() == null ? event.isPaid() : request.getPaid())
                .requestModeration(request.getRequestModeration() == null ? event.isRequestModeration() : request.getRequestModeration())
                .annotation(request.getAnnotation() == null ? event.getAnnotation() : request.getAnnotation())
                .description(request.getDescription() == null ? event.getDescription() : request.getDescription())
                .state(request.getStateAction() == null ? event.getState() : toState(request.getStateAction()))
                .title(request.getTitle() == null ? event.getTitle() : request.getTitle())
                .createdOn(event.getCreatedOn())
                .eventDate(request.getEventDate() == null ? event.getEventDate() : request.getEventDate())
                .publishedOn(event.getEventDate())
                .build();
    }

    public static Constant.State toState(Constant.StateAction action) {
        if (action.equals(Constant.StateAction.SEND_TO_REVIEW)) {
            return Constant.State.PENDING;
        } else if (action.equals(Constant.StateAction.PUBLISH_EVENT)) {
            return Constant.State.PUBLISHED;
        } else {
            return Constant.State.CANCELED;
        }
    }
}
