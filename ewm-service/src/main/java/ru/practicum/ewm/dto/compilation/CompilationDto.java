package ru.practicum.ewm.dto.compilation;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.dto.event.EventShortDto;

import java.util.Set;

@Getter
@Builder
public class CompilationDto {
    private Long id;
    private Set<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
