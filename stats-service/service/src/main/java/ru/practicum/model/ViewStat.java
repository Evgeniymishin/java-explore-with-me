package ru.practicum.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStat {
    private String app;
    private String uri;
    private Long hits;
}
