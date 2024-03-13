package ru.practicum.ewm.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.dto.user.mapper.UserListMapper;
import ru.practicum.ewm.dto.user.mapper.UserMapper;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repositiry.UserRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserListMapper listMapper;

    /**
     * Сохранение нового пользователя
     */
    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        return mapper.modelToDto(repository.save(mapper.dtoToModel(userDto)));
    }

    /**
     * Получение списка пользователей по их id
     * информация включает личные данные пользователей
     */
    @Override
    @Transactional
    public List<UserDto> findUser(Set<Long> ids, Pageable page) {
        return listMapper.modelsToDtos(repository.findAllByList(ids, page));
    }

    /**
     * Удаление пользователя
     */
    @Override
    public void deleteUser(long userId) throws ConstraintViolationException {
        if (repository.deleteByIdAndReturnCount(userId) == 0) {
            throw new NoFoundObjectException(String.format("Пользователь с id='%s' не найден", userId));
        }
    }

    /**
     * Валидация userId
     */
    @Override
    public User getUserByIdIfExist(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NoFoundObjectException(String.format("Пользователь с id='%s' не найден", userId)));
    }
}
