package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidationException;
import ru.practicum.main.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Не найдено событие с id = " + eventId));
        if (event.getState() != Constant.State.PUBLISHED)
            throw new ConflictException("Нельзя оставить комментарий к неопубликованному событию.");
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(
                newCommentDto,
                userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден пользователь с id = " + userId)),
                event)
        ));
    }

    @Override
    public CommentDto update(Long userId, Long commentId, NewCommentDto newCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Не найден коммент с id = " + commentId));
        if (!userId.equals(comment.getAuthor().getId()))
            throw new ValidationException("Нельзя редактировать чужой комментарий.");
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toCommentUpdated(newCommentDto, comment)));
    }

    @Override
    public void delete(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Не найден коммент с id = " + commentId));
        if (!userId.equals(comment.getAuthor().getId()))
            throw new ValidationException("Нельзя удалить чужой комментарий.");
        commentRepository.delete(comment);
    }
}
