package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.service.PrivateCommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {
    private final PrivateCommentService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CommentDto create(@PathVariable Long userId,
                      @PathVariable Long eventId,
                      @Valid @RequestBody NewCommentDto newCommentDto) {
        return service.create(userId, eventId, newCommentDto);
    }

    @PatchMapping(path = "/{commentId}")
    CommentDto update(@PathVariable Long userId,
                      @PathVariable Long commentId,
                      @Valid @RequestBody NewCommentDto newCommentDto) {
        return service.update(userId, commentId, newCommentDto);
    }

    @DeleteMapping(path = "/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long userId,
                @PathVariable Long commentId) {
        service.delete(userId, commentId);
    }
}
