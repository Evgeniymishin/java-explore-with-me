package ru.practicum.main.request.dto;

import lombok.*;
import ru.practicum.Constant;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private Constant.StateParticipation status;
    private LocalDateTime created;
}
