package ru.practicum.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.Constant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndPointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @DateTimeFormat(pattern = Constant.DATEFORMAT)
    private LocalDateTime timestamp;
}
