package ru.practicum.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListViewStats {
    private List<ViewStat> viewStats;
}
