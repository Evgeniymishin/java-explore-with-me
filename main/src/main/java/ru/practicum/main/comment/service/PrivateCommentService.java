package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;

public interface PrivateCommentService {
    CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto update(Long userId, Long commentId, NewCommentDto newCommentDto);

    void delete(Long userId, Long commentId);
}
