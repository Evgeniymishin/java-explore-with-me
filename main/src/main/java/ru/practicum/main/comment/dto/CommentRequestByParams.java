package ru.practicum.main.comment.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.Constant;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestByParams {
    private List<Long> eventIds;
    private List<Long> userIds;
    @DateTimeFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime rangeEnd;
    @PositiveOrZero
    private int from = 0;
    @Positive
    private int size = 10;
}
