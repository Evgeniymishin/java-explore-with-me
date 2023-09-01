package ru.practicum.main.event.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private Long id;
    @Min(-180)
    @Max(180)
    private float lat;
    @Min(-180)
    @Max(180)
    private float lon;
}
