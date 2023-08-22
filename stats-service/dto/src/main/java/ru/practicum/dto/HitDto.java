package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
