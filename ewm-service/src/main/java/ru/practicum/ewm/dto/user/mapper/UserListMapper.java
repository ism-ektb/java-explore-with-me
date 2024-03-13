package ru.practicum.ewm.dto.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {
    List<UserDto> modelsToDtos(List<User> users);
}
