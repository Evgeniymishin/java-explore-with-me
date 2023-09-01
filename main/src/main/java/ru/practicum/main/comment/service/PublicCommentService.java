package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;

import java.util.List;

public interface PublicCommentService {

    CommentDto get(Long commentId);

    List<CommentDto> getAll(CommentRequestByParams request);
}
