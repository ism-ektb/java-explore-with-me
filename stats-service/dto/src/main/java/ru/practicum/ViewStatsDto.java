package ru.practicum;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ViewStatsDto {
    private String app;
    private String uri;
    private long hits;
}
