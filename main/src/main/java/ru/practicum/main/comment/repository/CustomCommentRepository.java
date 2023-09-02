package ru.practicum.main.comment.repository;

import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.model.Comment;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findAllByRequest(CommentRequestByParams request);
}
