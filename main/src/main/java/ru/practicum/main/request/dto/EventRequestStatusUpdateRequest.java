package ru.practicum.main.request.dto;

import lombok.*;
import ru.practicum.Constant;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private Constant.StateParticipation status;
}



