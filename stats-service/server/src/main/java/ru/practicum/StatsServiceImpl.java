package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.EndPointHitMapper;
import ru.practicum.mapper.ViewStatsListMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final EndPointHitMapper mapper;
    private final ViewStatsListMapper listMapper;
    private final StatsRepository repository;

    /**
     * Метод получения обощенных данных о посейщениях возвращает число посейщений эндпойнтов переданных
     * в @param uris если это паратетр равен null, то возвращается информация по всем эндпойнтам
     * если @param unique true то считается число посейщений с уникальным ip
     */
    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) return listMapper.modelsToDtos(repository.getStatsIpUnique(start, end, uris));
        else return listMapper.modelsToDtos(repository.getStats(start, end, uris));
    }

    /**
     * Метод сохранения элемента статистики
     *
     * @param endPointHitDto
     * @return возвращает элемент статистики с присвоинным id
     */
    @Override
    public EndPointHitDto save(EndPointHitDto endPointHitDto) {
        return mapper.modelToTdo(repository.save(mapper.dtoToModel(endPointHitDto)));
    }
}
