package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStat {
    @JsonProperty("app")
    private String app;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("hits")
    private Long hits;
}
