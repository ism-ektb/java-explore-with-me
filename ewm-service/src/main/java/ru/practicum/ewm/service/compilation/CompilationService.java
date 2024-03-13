package ru.practicum.ewm.service.compilation;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    /**
     * Получение подборки событий
     * В случае, если по заданным фильтрам не найдено ни одной подборки, возвращает пустой список
     */
    List<CompilationDto> getCompilationsPublic(boolean pinned, Pageable pageable);

    /**
     * подборки по Id
     */
    CompilationDto getCompilationByIdPublic(long compId);

    /**
     * Добавление новой подборки событий
     */
    CompilationDto add(NewCompilationDto dto);

    /**
     * Удаление иподборки событий
     */
    void delete(long compId);

    /**
     * обновление подборки событий
     */
    CompilationDto update(long compId, UpdateCompilationDto dto);
}