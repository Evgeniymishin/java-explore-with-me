package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.dto.LocationDto;
import ru.practicum.main.event.model.Location;

@UtilityClass
public class LocationMapper {
    public LocationDto toLocationDto(Location location) {
        return LocationDto
                .builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto locationDto) {
        return Location
                .builder()
                .id(locationDto.getId())
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }
}
