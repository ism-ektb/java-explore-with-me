package ru.practicum.ewm.dto.compilation;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
@Jacksonized
public class NewCompilationDto {

    private Set<Long> events;
    @Builder.Default
    private Boolean pinned = false;
    @NotBlank
    @Size(min = 1, max = 50, message = "Длина заголовка должна быть от 1 до 50 знаков")
    private String title;
}
