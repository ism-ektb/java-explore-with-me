package ru.practicum.ewm.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.ewm.dto.request.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_and_event", columnNames = { "event_id", "requester_id" })})
@Getter
@NoArgsConstructor
@SuperBuilder
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "created")
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Setter
    private RequestStatus status = RequestStatus.PENDING;
}
