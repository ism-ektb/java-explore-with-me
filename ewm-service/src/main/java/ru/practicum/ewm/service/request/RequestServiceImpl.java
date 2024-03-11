package ru.practicum.ewm.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.RequestStatus;
import ru.practicum.ewm.dto.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.dto.request.mapper.RequestListMapper;
import ru.practicum.ewm.dto.request.mapper.RequestLongMapper;
import ru.practicum.ewm.exception.exception.BaseRelationshipException;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repositiry.RequestRepository;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.user.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final UserService userService;
    private final EventService eventService;
    private final RequestRepository repository;
    private final RequestLongMapper longMapper;
    private final ParticipationRequestMapper mapper;
    private final RequestListMapper listMapper;

    /**
     * Получение информации о заявках текущего пользователя в чужих событиях
     * Если ничего не найдено возвращается пустой список
     */
    @Override
    @Transactional
    public List<ParticipationRequestDto> getRequestByUserIdByUser(long userId) {
        //проверяем существует ли пользователь
        userService.getUserByIdIfExist(userId);

        return listMapper.modelsToDtos(repository.findAllByRequesterId(userId));
    }

    /**
     * Добавление запроса текущего пользователя на участие в событии
     * нельзя добавить повторный запрос (Ожидается код ошибки 409)
     * инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
     * нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
     * если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
     * если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в
     * состояние подтвержденного
     */
    @Override
    @Transactional
    public ParticipationRequestDto addRequestByUser(long userId, long eventId) {
        // проверяем валидность входных данных
        User user = userService.getUserByIdIfExist(userId);
        Event event = eventService.getAndCheckEventById(eventId);

        // преобразуем входные данные в сущность, и проверяем их правильность
        ParticipationRequest request = longMapper.longToModel(user, event);

        // сохраняем сущность и проверяем её уникальность
        return mapper.modelToDto(repository.save(request));
    }

    /**
     * отмена своего запроса на участие в событии
     */
    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        //Проверяем входные данные
        User user = userService.getUserByIdIfExist(userId);
        ParticipationRequest request = repository.findById(requestId)
                .orElseThrow(() -> new BaseRelationshipException(String.format("Запрос с id '%s' не найден", requestId)));
        if (!(request.getRequester().equals(user)))
            throw new BaseRelationshipException(String.format("Отменить запрос c id: '%s' может только" +
                    " реквестор c id: '%s'", requestId, userId));
        //Отклоняем заявку
        request.setStatus(RequestStatus.CANCELED);
        return mapper.modelToDto(repository.save(request));
    }
}
