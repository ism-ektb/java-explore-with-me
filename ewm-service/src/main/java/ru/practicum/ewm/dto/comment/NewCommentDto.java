package ru.practicum.ewm.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@Jacksonized
public class NewCommentDto {

    @Size(min = 10, max = 2000, message = "Минимальная длина комментария 10 символов, максимальная - 2000")
    @NotBlank
    private String text;
    @Size(min = 3, max = 20, message = "Минимальная длина комментария 3 символов, максимальная - 20")
    @NotBlank
    private String title;
}