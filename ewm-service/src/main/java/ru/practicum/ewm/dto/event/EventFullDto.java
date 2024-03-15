package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.comment.CommentForEventDto;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.dto.location.LocationDto;
import ru.practicum.ewm.dto.user.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Jacksonized
@EqualsAndHashCode
@ToString
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private UserShortDto initiator;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private State state;
    private String title;
    private long views;
    private List<CommentForEventDto> comments;
}

