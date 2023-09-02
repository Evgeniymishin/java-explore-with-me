package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.dto.NewCommentDto;

import java.util.List;

public interface AdminCommentService {

    CommentDto update(Long commentId, NewCommentDto newCommentDto);

    void delete(Long commentId);

    List<CommentDto> getAll(CommentRequestByParams request);
}
