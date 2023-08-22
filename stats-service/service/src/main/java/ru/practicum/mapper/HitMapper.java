package ru.practicum.mapper;

import ru.practicum.dto.HitDto;
import ru.practicum.model.Hit;

public class HitMapper {

    public static Hit toHit(HitDto hitDto) {
        return new Hit(
                hitDto.getId(),
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp()
        );
    }

    public static HitDto toDto(Hit hit) {
        return new HitDto(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}