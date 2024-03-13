package ru.practicum.ewm.dto.compilation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.event.mapper.EventSetMapper;
import ru.practicum.ewm.model.Compilation;

@Mapper(componentModel = "spring", uses = EventSetMapper.class)
public interface CompilationMapper {

    Compilation dtoToModel(NewCompilationDto compilationDto);

    CompilationDto modelToDto(Compilation compilation);

}
