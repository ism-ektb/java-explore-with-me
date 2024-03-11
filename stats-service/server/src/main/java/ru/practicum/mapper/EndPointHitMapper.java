package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.EndPointHitDto;
import ru.practicum.model.EndPointHit;

@Mapper(componentModel = "spring")
public interface EndPointHitMapper {

    EndPointHitDto modelToTdo(EndPointHit endPointHit);

    EndPointHit dtoToModel(EndPointHitDto endPointHitDto);
}
