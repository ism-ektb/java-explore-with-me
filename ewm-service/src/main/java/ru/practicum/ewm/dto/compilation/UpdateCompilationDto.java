package ru.practicum.ewm.dto.compilation;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Getter
public class UpdateCompilationDto {

    private Set<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Длина заголовка должна быть от 1 до 50 знаков")
    private String title;
}
