package ru.practicum.ewm.dto.location;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class LocationDto {

    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
}
