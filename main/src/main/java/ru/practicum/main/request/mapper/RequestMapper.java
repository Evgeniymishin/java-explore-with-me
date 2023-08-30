package ru.practicum.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Constant;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.Request;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {
    public static ParticipationRequestDto toParticipationDto(Request request) {
        return ParticipationRequestDto
                .builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(LocalDateTime.parse(request.getCreated().format(Constant.FORMATTER), Constant.FORMATTER))
                .build();

    }
}
