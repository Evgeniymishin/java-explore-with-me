package ru.practicum.main.compilation.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompilationRequest {
    @Size(min = 1, max = 50)
    private String title;
    private Boolean pinned;
    @Nullable
    private Set<Long> events;
}
