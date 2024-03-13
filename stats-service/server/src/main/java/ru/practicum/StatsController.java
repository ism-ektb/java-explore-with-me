package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.annotation.ValidStartIsBeforeEnd;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class StatsController {
    private final StatsService service;

    /**
     * Метод получения статистики.
     * Дата должна передоваться в формате yyyy-MM-dd HH:mm:ss, сатарт должен быть раньше конца
     * Если поле uris отсутствует, то статистика передается по всем эндпоинтам
     */
    @GetMapping(path = "/stats")
    @ValidStartIsBeforeEnd
    public List<ViewStatsDto> getStats(@RequestParam LocalDateTime start,
                                       @RequestParam(required = false,
                                               defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }

    /**
     * Метод сохраняет Hit в базу данных
     * Провеоряет передоваемый объект на наличие пустых полей, так же проверяется поле с IP адресом
     * на сообветствие формату. Дата должна передаваться в формате yyyy-MM-dd HH:mm:ss
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndPointHitDto save(@RequestBody @Valid EndPointHitDto endPointHitDto) {
        return service.save(endPointHitDto);
    }
}
