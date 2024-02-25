package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring", uses = ViewStatsMapper.class)
public interface ViewStatsListMapper {
    List<ViewStatsDto> modelsToDtos(List<ViewStats> viewStatsList);
}
