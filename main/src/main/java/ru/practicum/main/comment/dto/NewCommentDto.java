package ru.practicum.main.comment.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommentDto {
        @NotNull
        @Size(min = 3, max = 10000)
        private String text;
}
