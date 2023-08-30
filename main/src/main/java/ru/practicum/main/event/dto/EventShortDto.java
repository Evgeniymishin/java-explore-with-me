package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.Constant;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private Long id;
    private CategoryDto category;
    private UserShortDto initiator;
    private long confirmedRequests;
    private long views;
    private boolean paid;
    private String annotation;
    private String title;
    @JsonFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime eventDate;
}
