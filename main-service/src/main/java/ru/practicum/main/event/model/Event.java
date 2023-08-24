package ru.practicum.main.event.model;

import lombok.*;
import ru.practicum.Constant;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User initiator;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    private String annotation;
    private String description;
    @Enumerated(EnumType.STRING)
    private Constant.State state;
    private String title;
    private LocalDateTime createdOn;
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn;
}
