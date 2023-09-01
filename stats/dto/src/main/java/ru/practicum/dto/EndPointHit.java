package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.Constant;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndPointHit {
    private Long id;
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATEFORMAT)
    private LocalDateTime timestamp;
}
