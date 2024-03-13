package ru.practicum.ewm.dto.location.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.location.LocationDto;
import ru.practicum.ewm.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location dtoToModel(LocationDto locationDto);

    LocationDto modelToDto(Location location);
}
