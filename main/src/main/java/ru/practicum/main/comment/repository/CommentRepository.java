package ru.practicum.main.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.comment.dto.CommentView;
import ru.practicum.main.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {
    List<Comment> findAllByEventIdIn(List<Long> eventIds);

    @Query(value = "SELECT event_id as eventId, count(id) as count " +
            "FROM comments " +
            "WHERE event_id IN :eventIds " +
            "GROUP BY eventId",
            nativeQuery = true)
    List<CommentView> findRequestsByEventIds(List<Long> eventIds);
}
