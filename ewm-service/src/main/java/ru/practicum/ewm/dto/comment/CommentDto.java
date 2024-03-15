package ru.practicum.ewm.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
public class CommentDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private Long eventId;
    private UserShortDto author;
    private String text;
    private String title;
}
