package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.Constant;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @NotNull
    private Long category;
    @NotNull
    @Valid
    private LocationDto location;
    @PositiveOrZero
    private int participantLimit = 0;
    private boolean paid;
    private boolean requestModeration = true;
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    @Future
    @NotNull
    @JsonFormat(pattern = Constant.DATEFORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;
}
