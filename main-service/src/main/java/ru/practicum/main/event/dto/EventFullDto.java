package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.Constant;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.model.Location;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto implements Comparable<EventFullDto> {
    private Long id;
    private CategoryDto category;
    private Location location;
    private UserShortDto initiator;
    private long confirmedRequests;
    private long views;
    private int participantLimit;
    private boolean paid;
    private boolean requestModeration;
    private String annotation;
    private String description;
    private Constant.State state;
    private String title;
    @JsonFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime createdOn;
    @JsonFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime eventDate;
    @JsonFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime publishedOn;

    @Override
    public int compareTo(EventFullDto o) {
        if (this.getEventDate().isBefore(o.getEventDate())) {
            return -1;
        } else if (this.getEventDate().isAfter(o.getEventDate())) {
            return 1;
        } else {
            return 0;
        }
    }
}