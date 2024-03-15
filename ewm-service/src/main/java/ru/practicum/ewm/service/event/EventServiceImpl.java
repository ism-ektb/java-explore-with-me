package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.GetClient;
import ru.practicum.ViewStatsDto;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.event.enums.SortVariant;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.dto.event.mapper.EventListMapper;
import ru.practicum.ewm.dto.event.mapper.EventMapper;
import ru.practicum.ewm.dto.event.mapper.EventUriMapper;
import ru.practicum.ewm.dto.event.patcher.EventPatcher;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.request.RequestStatus;
import ru.practicum.ewm.dto.request.mapper.RequestListMapper;
import ru.practicum.ewm.exception.exception.BaseRelationshipException;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repositiry.EventRepository;
import ru.practicum.ewm.repositiry.RequestRepository;
import ru.practicum.ewm.service.category.CategoryService;
import ru.practicum.ewm.service.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ru.practicum.ewm.dto.event.enums.State.CANCELED;
import static ru.practicum.ewm.dto.event.enums.State.PUBLISHED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final RequestRepository requestRepository;
    private final EventMapper mapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final EventListMapper listMapper;
    private final EventPatcher patcher;
    private final RequestListMapper requestListMapper;
    private final GetClient multiClient;
    private final EventUriMapper uriMapper;


    /**
     * Получение событий добавленных текущим пользователем
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    @Override
    public List<EventShortDto> getEventsByUser(long userId, PageRequest pageRequest) {
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);
        //запрос статистики и формирование ответа
        List<String> uris = uriMapper.modelsToUris(events);
        List<ViewStatsDto> viewStatsDtoList = multiClient.readStat(uris);
        return listMapper.modelsToDtos(events, viewStatsDtoList);
    }

    /**
     * Добавление нового события
     */
    @Override
    public EventFullDto saveEvent(long userId, NewEventDto newEventDto) {
        //Валидация входных данных
        userService.getUserByIdIfExist(userId);
        categoryService.findCatById(newEventDto.getCategory());
        //сохраняем
        Event event = mapper.dtoToModel(newEventDto, userId);
        Event saveResultsEvent = repository.save(event);
        //формируем ответ
        List<String> uris = uriMapper.modelsToUris(List.of(saveResultsEvent));
        List<ViewStatsDto> list = multiClient.readStat(uris);
        long views = list.isEmpty() ? 0L : list.get(0).getHits();
        return mapper.modelToDto(saveResultsEvent, views);
    }

    /**
     * Получение полной информации о событии добавленой текущим пользователем
     * В случае, если события с заданным id не найдено, возвращается ошибка
     */
    @Override
    public EventFullDto getEventByIdByUser(long userId, long eventId) {
        Event event = repository.findByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new NoFoundObjectException(String.format(
                        "Событие с id='%s' у пользователя с id='%s' не найдено", eventId, userId)));
        //формируем ответ
        List<String> uris = uriMapper.modelsToUris(List.of(event));
        List<ViewStatsDto> list = multiClient.readStat(uris);
        long views = list.isEmpty() ? 0L : list.get(0).getHits();
        return mapper.modelToDto(event, views);
    }

    /**
     * Изменение события добавленного текущим пользователем
     * изменить можно только отмененные события или события в состоянии ожидания модерации
     * дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @Override
    public EventFullDto patchEventByUser(long userId, long eventId, UpdateEventUserRequest updateEventUserRequest) {
        //валидация входных данных
        Event event = repository.findByInitiatorIdAndId(userId, eventId).orElseThrow(() ->
                new NoFoundObjectException(String.format(
                        "Событие с id='%s' у пользователя с id='%s' не найдено", eventId, userId)));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))
                || event.getState() == PUBLISHED) throw new BaseRelationshipException(
                String.format("Событие с id='%s' не может быть изменено", eventId));
        //обновление и сохранение результата
        Event patchEvent = patcher.userPatch(event, updateEventUserRequest);
        Event saveResultsEvent = repository.save(patchEvent);
        //формируем ответ
        List<String> uris = uriMapper.modelsToUris(List.of(saveResultsEvent));
        List<ViewStatsDto> list = multiClient.readStat(uris);
        long views = list.isEmpty() ? 0L : list.get(0).getHits();
        return mapper.modelToDto(saveResultsEvent, views);
    }

    /**
     * Получение информации о запросах на участин в событиях текущего пользователя
     * В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
     */
    @Override
    public List<ParticipationRequestDto> getRequestsByEventIdByUser(long userId, long eventId) {
        //Валидация входных данных
        User user = userService.getUserByIdIfExist(userId);
        Event event = getAndCheckEventById(eventId);
        if (!(event.getInitiator().equals(user)))
            throw new BaseRelationshipException(String.format("Пользователь с id '%ы' не является иницматором " +
                    "события с id '%s'", userId, eventId));
        //формирование ответа
        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);
        return requestListMapper.modelsToDtos(requests);
    }

    /**
     * Изменение статуса заявок на участие в событии текущего пользователя
     * если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
     * нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
     * статус можно изменить только у заявок, находящихся в состоянии ожидания
     * если при подтверждении данной заявки, лимит заявок для события исчерпан,
     * то все неподтверждённые заявки необходимо отклонить
     */
    @Override
    public EventRequestStatusUpdateResponse conformRequestsByUser(
            long userId, long eventId, EventRequestStatusUpdateRequest updateRequests) {
        //проверяем входные данные
        User user = userService.getUserByIdIfExist(userId);
        Event event = getAndCheckEventById(eventId);
        if (!(event.getInitiator().equals(user)))
            throw new BaseRelationshipException(String.format("Пользователь" +
                    " с Id '%s' не инициатор события с id '%s'", userId, eventId));
        //рассматриваем заявки
        List<ParticipationRequest> waitList = requestRepository.findAllByIds(updateRequests.getRequestIds());
        List<ParticipationRequest> confirmed = new ArrayList<>();
        List<ParticipationRequest> rejected = new ArrayList<>();
        if (updateRequests.getStatus() == RequestStatus.REJECTED) {
            while (waitList.size() > 0) {
                ParticipationRequest req = waitList.remove(0);
                if (req.getStatus() == RequestStatus.CONFIRMED)
                    throw new BaseRelationshipException(String.format("нельзя отменить " +
                            "принятую заявку с id '%s'", req.getId()));
                if (req.getStatus() == RequestStatus.PENDING) req.setStatus(RequestStatus.REJECTED);
                rejected.add(req);
            }
        } else { //updateRequests.getStatus() == RequestStatus.CONFIRMED
            int freePlaces = event.getParticipantLimit() - event.getConfirmedRequests();
            if (freePlaces <= 0)
                throw new BaseRelationshipException(String.format("Лимит участников события с id '%s' достигнут", eventId));
            while (confirmed.size() <= freePlaces) {
                if (waitList.size() <= 0) break;
                ParticipationRequest req = waitList.remove(0);
                if (req.getStatus() == RequestStatus.PENDING) {
                    req.setStatus(RequestStatus.CONFIRMED);
                    confirmed.add(req);
                }
            }
            while (waitList.size() > 0) { //Если заявок больше чем свободных мест
                ParticipationRequest req = waitList.remove(0);
                req.setStatus(RequestStatus.CANCELED);
                rejected.add(req);
            }
        }
        //сохраняем результат
        EventRequestStatusUpdateResponse.EventRequestStatusUpdateResponseBuilder resp =
                EventRequestStatusUpdateResponse.builder();
        resp.confirmedRequests(requestListMapper.modelsToDtos(requestRepository.saveAll(confirmed)));
        resp.rejectedRequests(requestListMapper.modelsToDtos(requestRepository.saveAll(rejected)));

        return resp.build();
    }

    /**
     * метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     */
    @Override
    public List<EventFullDto> getEventsByAdmin(
            List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, PageRequest pageRequest) {
        List<Event> events = repository.findByManyParam(users, states, categories, rangeStart, rangeEnd, pageRequest);
        //запрос статистики и формирование ответа
        List<String> uris = uriMapper.modelsToUris(events);
        List<ViewStatsDto> viewStatsDtoList = multiClient.readStat(uris);
        return listMapper.modelsToFullDtos(events, viewStatsDtoList);
    }

    /**
     * Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:
     * <p>
     * дата начала изменяемого события должна быть не ранее чем за час от даты публикации.
     * событие можно публиковать, только если оно в состоянии ожидания публикации
     * событие можно отклонить, только если оно еще не опубликовано
     */
    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        //Проверяем входные данные
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NoFoundObjectException(String.format(
                        "Событие с id='%s'  не найдено", eventId)));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))
                || event.getState() == PUBLISHED || event.getState() == CANCELED)
            throw new BaseRelationshipException(
                    String.format("Событие с id='%s' не может быть изменено", eventId));
        //обновляем и сохраняем
        Event patchEvent = patcher.adminPatch(event, updateEventAdminRequest);
        Event saveResultsEvent = repository.save(patchEvent);
        //запрос статистики и формирование ответа
        List<String> uris = uriMapper.modelsToUris(List.of(saveResultsEvent));
        List<ViewStatsDto> list = multiClient.readStat(uris);
        long views = list.isEmpty() ? 0L : list.get(0).getHits();
        return mapper.modelToDto(saveResultsEvent, views);

    }

    /**
     * метод для валидации eventId по базе данных
     */
    @Override
    public Event getAndCheckEventById(Long eventId) {
        Event event = repository.findById(eventId).orElseThrow(() ->
                new NoFoundObjectException(String.format("Событие с id='%s' не найдено", eventId)));
        return event;
    }

    /**
     * публичный поиск по событиям
     * выдаются только опубликованные события
     * текстовый поиск (по аннотации и подробному описанию) без учета регистра букв
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     *
     * @param onlyAvailable true для событий без ограничения числа участников, либо на это событие еще можно подать
     *                      заявку
     * @param sort          Варианты сортировки. EVENT_DATE по дате или VIEWS по числу просмотров
     */

    @Override
    public List<EventShortDto> getAllEvents(
            String text, List<Long> categories, Boolean paid, LocalDateTime startDate,
            LocalDateTime endDate, Boolean onlyAvailable, SortVariant sort,
            PageRequest pageRequest) {
        List<Event> events = repository.findByManyParams(text, categories, paid, startDate, endDate,
                onlyAvailable, pageRequest);
        //Запрос статистики и формирование ответа
        List<String> uris = uriMapper.modelsToUris(events);
        List<ViewStatsDto> viewStatsDtoList = multiClient.readStat(uris);
        List<EventShortDto> dtoList = listMapper.modelsToDtos(events, viewStatsDtoList);
        if (sort == SortVariant.VIEWS) {
            dtoList.sort(Comparator.comparing(EventShortDto::getViews));
        }
        return dtoList;
    }

    /**
     * Получение информации об опубликованном событии
     */
    @Override
    public EventFullDto getEventById(Long eventId) {
        Event event = repository.findByIdAndState(eventId, PUBLISHED).orElseThrow(() ->
                new NoFoundObjectException(String.format("Событие с Id '%s' не найдено или не опубликовано", eventId)));
        //Запрос статистики и формирование ответа
        List<String> uris = uriMapper.modelsToUris(List.of(event));
        List<ViewStatsDto> list = multiClient.readStat(uris);
        long views = list.isEmpty() ? 0L : list.get(0).getHits();
        return mapper.modelToDto(event, views);
    }
}
