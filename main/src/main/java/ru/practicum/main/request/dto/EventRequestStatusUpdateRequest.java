package ru.practicum.main.request.dto;

import lombok.*;
import ru.practicum.Constant;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private Constant.StateParticipation status;
}