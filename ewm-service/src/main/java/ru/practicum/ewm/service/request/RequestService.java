package ru.practicum.ewm.service.request;

import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    /**
     * Получение информации о заявках текущего пользователя в чужих событиях
     * Если ничего не найдено возвращается пустой список
     */
    List<ParticipationRequestDto> getRequestByUserIdByUser(long userId);

    /**
     * Добавление запроса текущего пользователя на участие в событии
     * нельзя добавить повторный запрос (Ожидается код ошибки 409)
     * инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
     * нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
     * если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
     * если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в
     * состояние подтвержденного
     */
    ParticipationRequestDto addRequestByUser(long userId, long eventId);

    /**
     * отмена своего запроса на участие в событии
     */
    ParticipationRequestDto cancelRequest(long userId, long requestId);
}