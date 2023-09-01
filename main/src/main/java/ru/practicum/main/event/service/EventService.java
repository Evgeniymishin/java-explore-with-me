package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.model.ConfirmedRequests;
import ru.practicum.main.request.repository.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final RequestRepository requestRepository;
    private final CommentRepository commentRepository;
    private final StatsClient stats;

    protected Map<Long, Integer> getConfirmedRequests(List<Event> events) {
        return requestRepository.findRequestsByEventIds(events.stream().map(Event::getId)
                        .collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(k -> k.getEventId(), ConfirmedRequests::getCount));
    }

    protected Map<Long, Long> getStats(List<Event> events) {
        return stats.getAllForClient(events.stream()
                        .map(event -> "/events/" + event.getId())
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(k -> Long.valueOf(k.getUri().replace("/events/", "")),
                        ViewStat::getHits));
    }

    protected void sendViews(HttpServletRequest request) {
        stats.create(EndPointHit
                .builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build());
    }

    protected Map<Long, List<CommentDto>> getComments(List<Event> events) {
        List<Comment> comments = commentRepository.findAllByEventIdIn(events.stream().map(event -> event.getId()).collect(Collectors.toList()));
        if (comments.size() == 0) {
            return Collections.emptyMap();
        }
        Map<Long, List<CommentDto>> commentsMap = new HashMap<>();
        for (Comment comment : comments) {
            if (commentsMap.get(comment.getEvent().getId()) == null) {
                commentsMap.put(comment.getEvent().getId(), List.of(CommentMapper.toCommentDto(comment)));
            } else {
                commentsMap.get(comment.getEvent().getId()).add(CommentMapper.toCommentDto(comment));
            }
        }
        return commentsMap;
    }
}
