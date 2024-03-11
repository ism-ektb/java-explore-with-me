package ru.practicum.ewm.dto.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@EqualsAndHashCode
public class UserDto {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 250, message = "Значение должно быть больше 2 и меньше 250")
    private String name;
    @Email
    @NotBlank
    @Size(min = 6, max = 254, message = "Значение должно быть больше 6 и меньше 254")
    private String email;
}