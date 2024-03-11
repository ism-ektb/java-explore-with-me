package ru.practicum;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    /**
     * Метод получения обощенных данных о посейщениях возвращает число посейщений эндпойнтов переданных
     * в @param uris если это паратетр равен null, то возвращается информация по всем эндпойнтам
     * если @param unique true то считается число посейщений с уникальным ip
     */
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    /**
     * Метод сохранения элемента статистики
     *
     * @return возвращает элемент статистики с присвоинным id
     */
    EndPointHitDto save(EndPointHitDto endPointHitDto);
}
