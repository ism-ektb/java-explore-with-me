package ru.practicum.ewm.dto.location.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.ewm.dto.location.LocationDto;

class LocationMapperTest {

    private final LocationMapper mapper = new LocationMapperImpl();

    @Test
    void modelToDto() {
        LocationDto locationDto = LocationDto.builder().lat(60.2F).lon(60.3F).build();
        Assertions.assertEquals(mapper.modelToDto(mapper.dtoToModel(locationDto)), locationDto);
    }

    @Test
    void dtoToModel() {
    }
}