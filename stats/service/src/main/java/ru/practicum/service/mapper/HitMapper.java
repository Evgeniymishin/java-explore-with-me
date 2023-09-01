package ru.practicum.service.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Constant;
import ru.practicum.dto.EndPointHit;
import ru.practicum.service.model.Hit;


import java.time.LocalDateTime;

@UtilityClass
public class HitMapper {

    public static Hit toHit(EndPointHit endPointHit) {
        return Hit
                .builder()
                .id(endPointHit.getId())
                .app(endPointHit.getApp())
                .uri(endPointHit.getUri())
                .ip(endPointHit.getIp())
                .timestamp(LocalDateTime.parse(endPointHit.getTimestamp().format(Constant.FORMATTER), Constant.FORMATTER))
                .build();
    }

    public static EndPointHit toDto(Hit hit) {
        return EndPointHit
                .builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();

    }
}