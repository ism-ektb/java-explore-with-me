package ru.practicum.ewm.dto.category;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@EqualsAndHashCode
public class CategoryDto {
    private long id;
    @NotBlank
    @Size(min = 2, max = 50, message = "Значение должно быть больше 2 и меньше 50")
    private String name;
}
