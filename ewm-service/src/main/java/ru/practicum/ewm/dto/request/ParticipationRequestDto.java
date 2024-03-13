package ru.practicum.ewm.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 *
 * Заявка на участие в событии
 */
@Getter
@Builder
@JsonFormat
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @NotNull
    @Positive
    private Long event;
    private Long id;
    @NotNull
    @Positive
    private Long requester;
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;
}
