package ru.practicum.ewm.dto.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto modelToDto(User user);

    User dtoToModel(UserDto userDto);

    UserShortDto modelToShortDto(User user);

    @Mapping(source = "userId", target = "id")
    User longToModel(Long userId);

}
