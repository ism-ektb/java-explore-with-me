package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.annotation.FuturePlusTwoHours;
import ru.practicum.ewm.dto.event.enums.StateUserAction;
import ru.practicum.ewm.dto.location.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "Значение не должно быть меньше 20 и больше 2000")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "Значение не должно быть меньше 20 и больше 7000")
    private String description;
    @FuturePlusTwoHours
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @Valid
    private LocationDto location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateUserAction stateAction;
    @Size(min = 3, max = 120, message = "Значение не должно быть меньше 3 и больше 120")
    private String title;
}
