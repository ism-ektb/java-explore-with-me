package ru.practicum.ewm.dto.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@EqualsAndHashCode
public class UserShortDto {
    @NotNull
    Long id;
    @NotBlank
    String name;
}
