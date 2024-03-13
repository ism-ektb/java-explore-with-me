package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.annotation.FuturePlusTwoHours;
import ru.practicum.ewm.dto.location.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@ToString
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000, message = "Значение должно быть больше 20 и меньше 2000")
    private String annotation;
    @NotNull
    private long category;
    @NotBlank
    @Size(min = 20, max = 7000, message = "Значение должно быть больше 20 и меньше 7000")
    private String description;
    @NotNull
    @FuturePlusTwoHours
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    @Valid
    private LocationDto location;
    @Builder.Default
    private boolean paid = false;
    @Builder.Default
    @PositiveOrZero
    private int participantLimit = 0;
    @Builder.Default
    private boolean requestModeration = true;
    @NotBlank
    @Size(min = 3, max = 120, message = "Значение должно быть больше 3 и меньше 120")
    private String title;
}
