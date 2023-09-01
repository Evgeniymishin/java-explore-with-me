package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.service.AdminCommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {

    private final AdminCommentService service;

    @PatchMapping("/{commentId}")
    CommentDto update(@PathVariable Long commentId,
                      @Valid @RequestBody NewCommentDto newCommentDto) {
        return service.update(commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long commentId) {
        service.delete(commentId);
    }

    @GetMapping
    List<CommentDto> getAll(CommentRequestByParams request) {
        return service.getAll(request);
    }
}
