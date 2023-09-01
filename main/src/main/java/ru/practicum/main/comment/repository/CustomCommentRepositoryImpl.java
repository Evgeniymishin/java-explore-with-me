package ru.practicum.main.comment.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.Constant;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {
    private final EntityManager manager;

    @Override
    public List<Comment> findAllByRequest(CommentRequestByParams request) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
        Root<Comment> root = query.from(Comment.class);
        Predicate criteria = builder.conjunction();
        if (request.getEventIds() != null && request.getEventIds().size() > 0) {
            criteria = builder.and(criteria, root.get("event").in(request.getEventIds()));
        }
        if (request.getUserIds() != null && request.getUserIds().size() > 0) {
            criteria = builder.and(criteria, root.get("author").in(request.getUserIds()));
        }
        if (request.getRangeStart() != null) {
            criteria = builder.and(criteria, builder.greaterThanOrEqualTo(root.get("created").as(LocalDateTime.class),
                    LocalDateTime.parse(request.getRangeStart().format(Constant.FORMATTER), Constant.FORMATTER)));
        }
        if (request.getRangeEnd() != null) {
            criteria = builder.and(criteria, builder.lessThanOrEqualTo(root.get("created").as(LocalDateTime.class),
                    LocalDateTime.parse(request.getRangeEnd().format(Constant.FORMATTER), Constant.FORMATTER)));
        }
        return manager.createQuery(query.select(root).where(criteria)).setFirstResult(request.getFrom()).setMaxResults(request.getSize()).getResultList();
    }
}
