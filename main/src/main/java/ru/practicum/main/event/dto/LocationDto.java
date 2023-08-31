package ru.practicum.main.event.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private Long id;
    @Valid
    @Min(-180)
    @Max(180)
    private float lat;
    @Valid
    @Min(-180)
    @Max(180)
    private float lon;
}
