package ru.practicum;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@ToString
@Jacksonized
public class ViewStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
