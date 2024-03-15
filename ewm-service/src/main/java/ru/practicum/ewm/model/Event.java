package ru.practicum.ewm.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;
import ru.practicum.ewm.dto.event.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SuperBuilder
@Table(name = "events")
@EqualsAndHashCode
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Builder.Default
    @Column(name = "created")
    private LocalDateTime createdOn = LocalDateTime.now();
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    Location location;
    Boolean paid;
    @Column(name = "participant_limit")
    Integer participantLimit;
    @Column(name = "published")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.PENDING;
    @Column(name = "title")
    private String title;
    @Formula(value = "(SELECT COUNT(r.id) FROM requests as r WHERE r.event_id = id AND r.status LIKE 'CONFIRMED')")
    private Integer confirmedRequests;
    @OneToMany(mappedBy = "event")
    private List<Comment> comments;

}
