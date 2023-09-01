package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.Constant;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public static Comment toComment(NewCommentDto newCommentDto, User author, Event event) {
        return Comment
                .builder()
                .author(author)
                .event(event)
                .text(newCommentDto.getText())
                .created(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER))
                .updated(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER))
                .build();
    }

    public static Comment toCommentUpdated(NewCommentDto newCommentDto, Comment comment) {
        return Comment
                .builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .event(comment.getEvent())
                .text(newCommentDto.getText())
                .created(comment.getCreated())
                .updated(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER))
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .event(EventMapper.toShortEventDto(comment.getEvent()))
                .text(comment.getText())
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .build();
    }
}
