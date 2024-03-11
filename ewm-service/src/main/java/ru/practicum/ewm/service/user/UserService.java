package ru.practicum.ewm.service.user;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    /**
     * Сохранение нового пользователя
     */
    UserDto saveUser(UserDto userDto);

    /**
     * Получение списка пользователей по их id
     * информация включает личные данные пользователей
     */
    List<UserDto> findUser(Set<Long> ids, Pageable page);

    /**
     * Удаление пользователя
     */
    void deleteUser(long userId);

    User getUserByIdIfExist(Long userId);
}
