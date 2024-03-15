package ru.practicum.ewm.dto.comment;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Builder
@Getter
public class PatchCommentDto {
    @Size(min = 10, max = 2000, message = "Минимальная длина комментария 10 символов, максимальная - 2000")
    private String text;
    @Size(min = 3, max = 20, message = "Минимальная длина комментария 3 символов, максимальная - 20")
    private String title;
}
