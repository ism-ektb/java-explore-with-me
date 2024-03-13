package ru.practicum.ewm.dto.compilation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring", uses = CompilationMapper.class)
public interface CompilationListMapper {

    List<CompilationDto> modelsToDtos(List<Compilation> compilations);
}
