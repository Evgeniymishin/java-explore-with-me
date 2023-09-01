package ru.practicum.main.comment.dto;

import lombok.*;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private UserShortDto author;
    private EventShortDto event;
    private String text;
    private LocalDateTime created;
    private LocalDateTime updated;
}
