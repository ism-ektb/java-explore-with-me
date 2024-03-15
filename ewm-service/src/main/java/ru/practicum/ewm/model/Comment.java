package ru.practicum.ewm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "comments", uniqueConstraints = {
        @UniqueConstraint(name = "unique_author_and_event", columnNames = {"event_id", "author_id"})})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    @Column(name = "created")
    private LocalDateTime createdOn = LocalDateTime.now();
    private String title;
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

}