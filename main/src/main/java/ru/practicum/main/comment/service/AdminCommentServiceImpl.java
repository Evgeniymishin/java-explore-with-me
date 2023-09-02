package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentDto update(Long commentId, NewCommentDto newCommentDto) {
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toCommentUpdated(newCommentDto,
                commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Не найден комментарий с id = " + commentId)))));
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.delete(commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Не найден комментарий с id = " + commentId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAll(CommentRequestByParams request) {
        return commentRepository.findAllByRequest(request).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
