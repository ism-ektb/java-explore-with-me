package ru.practicum.ewm.service.event;

import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.event.enums.SortVariant;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    /**
     * Получение событий добавленных текущим пользователем
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventShortDto> getEventsByUser(long userId, PageRequest pageRequest);

    /**
     * Добавление нового события
     */
    EventFullDto saveEvent(long userId, NewEventDto newEventDto);

    /**
     * Получение полной информации о событии добавленой текущим пользователем
     * В случае, если события с заданным id не найдено, возвращается ошибка
     */
    EventFullDto getEventByIdByUser(long userId, long eventId);

    /**
     * Изменение события добавленного текущим пользователем
     * изменить можно только отмененные события или события в состоянии ожидания модерации
     * дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    EventFullDto patchEventByUser(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest);

    /**
     * Получение информации о запросах на участин в событиях текущего пользователя
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     */
    List<ParticipationRequestDto> getRequestsByEventIdByUser(long userId, long eventId);

    /**
     * Изменение статуса заявок на участие в событии текущего пользователя
     * если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     * нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
     * статус можно изменить только у заявок, находящихся в состоянии ожидания
     * если при подтверждении данной заявки, лимит заявок для события исчерпан,
     * то все неподтверждённые заявки необходимо отклонить
     */
    EventRequestStatusUpdateResponse conformRequestsByUser(
            long userId, long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    /**
     * метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventFullDto> getEventsByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        PageRequest pageRequest);

    /**
     * Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:
     * <p>
     * дата начала изменяемого события должна быть не ранее чем за час от даты публикации.
     * событие можно публиковать, только если оно в состоянии ожидания публикации
     * событие можно отклонить, только если оно еще не опубликовано
     */
    EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    /**
     * валидация eventId
     */
    Event getAndCheckEventById(Long eventId);

    /**
     * выдаются только опубликованные события
     * текстовый поиск (по аннотации и подробному описанию)  без учета регистра букв
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    List<EventShortDto> getAllEvents(String text, List<Long> categories, Boolean paid,
                                     LocalDateTime startDate, LocalDateTime endDate,
                                     Boolean onlyAvailable, SortVariant sort,
                                     PageRequest pageRequest);

    /**
     * Получение информации об опубликованном событии
     */
    EventFullDto getEventById(Long eventId);
}




