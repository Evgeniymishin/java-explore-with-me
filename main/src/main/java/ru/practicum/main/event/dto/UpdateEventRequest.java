package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.Constant;
import ru.practicum.main.event.model.Location;

import javax.validation.constraints.Future;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventRequest {
    private Long category;
    private Location location;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean paid;
    private Boolean requestModeration;
    @Size(min = 20, max = 2000)
    private String annotation;
    @Size(min = 20, max = 7000)
    private String description;
    private Constant.StateAction stateAction;
    @Size(min = 3, max = 120)
    private String title;
    @Future
    @JsonFormat(pattern = Constant.DATEFORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
}
