package ru.practicum.ewm.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationDto;
import ru.practicum.ewm.dto.compilation.mapper.CompilationListMapper;
import ru.practicum.ewm.dto.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.dto.compilation.patcher.CompilationPatch;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.repositiry.CompilationRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final CompilationMapper mapper;
    private final CompilationListMapper listMapper;
    private final CompilationPatch patcher;

    /**
     * Получение подборки событий
     * В случае, если по заданным фильтрам не найдено ни одной подборки, возвращает пустой список
     */
    @Override
    @Transactional
    public List<CompilationDto> getCompilationsPublic(boolean pinned, Pageable pageable) {
        List<Compilation> compilations = repository.getCompilationByPinned(pinned, pageable);
        return listMapper.modelsToDtos(compilations);
    }

    /**
     * подборки по Id
     */
    @Override
    public CompilationDto getCompilationByIdPublic(long compId) {
        Compilation compilation = repository.findById(compId).orElseThrow(() ->
                new NoFoundObjectException(String.format("Подборка с id '%s' отсутствует", compId)));
        return mapper.modelToDto(compilation);
    }

    /**
     * Добавление новой подборки событий
     */
    @Override
    public CompilationDto add(NewCompilationDto dto) {
        Compilation compilation = mapper.dtoToModel(dto);
        Compilation saveResultsCat = repository.save(compilation);
        return mapper.modelToDto(saveResultsCat);
    }

    /**
     * Удаление подборки событий
     */
    @Override
    public void delete(long compId) {
        //проверяем Валидность compId
        getCompilationByIdPublic(compId);
        repository.deleteById(compId);

    }

    /**
     * обновление подборки событий
     */
    @Override
    @Transactional
    public CompilationDto update(long compId, UpdateCompilationDto dto) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NoFoundObjectException(String.format("Подборка " +
                        "с id '%s' отсутствует", compId)));

        return mapper.modelToDto(repository.save(patcher.patch(compilation, dto)));
    }
}
