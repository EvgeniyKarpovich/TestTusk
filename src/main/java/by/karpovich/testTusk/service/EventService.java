package by.karpovich.testTusk.service;

import by.karpovich.testTusk.api.dto.EventDto;
import by.karpovich.testTusk.api.searchCriteriaDto.EventCriteriaDto;
import by.karpovich.testTusk.exception.DuplicateException;
import by.karpovich.testTusk.exception.NotFoundModelException;
import by.karpovich.testTusk.jpa.model.EventModel;
import by.karpovich.testTusk.jpa.repository.EventRepository;
import by.karpovich.testTusk.jpa.specification.EventSpecification;
import by.karpovich.testTusk.mappting.EventMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EventService {

    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventRepository eventRepository;

    public EventDto saveEvent(EventDto eventDto) {
        validateAlreadyExists(eventDto, null);
        EventModel eventModel = eventMapper.mapFromDto(eventDto);
        EventModel save = eventRepository.save(eventModel);
        log.info("IN save -  Event with id '{}' saved", eventDto.getId());
        return eventMapper.mapFromEntity(save);
    }

    public EventDto update(Long id, EventDto eventDto) {
        validateAlreadyExists(eventDto, id);
        EventModel eventModel = eventMapper.mapFromDto(eventDto);
        eventModel.setId(id);
        EventModel updated = eventRepository.save(eventModel);
        log.info("IN update -  Event with id '{}' updated", eventDto.getId());
        return eventMapper.mapFromEntity(updated);
    }

    public void deleteById(Long id) {
        if (eventRepository.findById(id).isPresent()) {
            eventRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Event with id = %s not found", id));
        }
        log.info("IN deleteById - Event with id = {} deleted", id);
    }

    public EventDto findById(Long id) {
        Optional<EventModel> eventModel = eventRepository.findById(id);
        EventModel event = eventModel.orElseThrow(
                () -> new NotFoundModelException(String.format("Event with id = %s not found", id)));
        log.info("IN findById -  Event with id = {} found", event.getId());
        return eventMapper.mapFromEntity(event);
    }

    public List<EventDto> findAllByCriteria(EventCriteriaDto eventCriteriaDto) {
        List<EventModel> eventModels = eventRepository.findAll(EventSpecification.createFromCriteria(eventCriteriaDto));
        log.info("IN findAll - the number events  = {}", eventModels.size());
        return eventMapper.mapFromListEntity(eventModels);
    }

    public Map<String, Object> findAllSort(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EventModel> eventModels = eventRepository.findAll(pageable);
        List<EventModel> content = eventModels.getContent();

        List<EventModel> sortEvent = sortEvent(content);

        List<EventDto> eventDtoList = eventMapper.mapFromListEntity(sortEvent);

        Map<String, Object> response = new HashMap<>();

        response.put("tutorials", eventDtoList);
        response.put("currentPage", eventModels.getNumber());
        response.put("totalItems", eventModels.getTotalElements());
        response.put("totalPages", eventModels.getTotalPages());

        log.info("IN findAll - the number events = {}", sortEvent.size());

        return response;
    }

    public Map<String, Object> findAllByDate(int page, int size, String date) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EventModel> eventModels = eventRepository.findAll(pageable);
        List<EventModel> content = eventModels.getContent();
        List<EventDto> eventDtoList = eventMapper.mapFromListEntity(content);

        List<EventDto> sortByTimeSpending = findByDate(eventDtoList, date);

        Map<String, Object> response = new HashMap<>();

        response.put("tutorials", sortByTimeSpending);
        response.put("currentPage", eventModels.getNumber());
        response.put("totalItems", eventModels.getTotalElements());
        response.put("totalPages", eventModels.getTotalPages());

        log.info("IN findAll - the number events = {}", eventDtoList.size());

        return response;
    }

    //Сортировка события , приоритет дата, затем тема и организатор (без учета регистра)
    public List<EventModel> sortEvent(List<EventModel> eventDtoList) {
        return eventDtoList.stream()
                .sorted(Comparator.comparing(EventModel::getTimeSpending)
                        .thenComparing((a, b) -> a.getTheme().compareToIgnoreCase(b.getTheme()))
                        .thenComparing((a, b) -> a.getOrganizer().compareToIgnoreCase(b.getOrganizer())))
                .collect(Collectors.toList());
    }

    public List<EventDto> findByDate(List<EventDto> eventDtoList, String date) {
        return eventDtoList.stream()
                .filter(eventDto -> eventDto.getTimeSpending().startsWith(date))
                .collect(Collectors.toList());
    }

    private void validateAlreadyExists(EventDto eventDto, Long id) {
        Optional<EventModel> eventModel = eventRepository.findByOrganizerAndLocation(eventDto.getOrganizer(), eventDto.getLocation());
        if (eventModel.isPresent() && !eventModel.get().getId().equals(id)) {
            throw new DuplicateException(String.format("such event already exist", eventDto.getTheme()));
        }
    }

    public List<EventModel> sortByTheme(List<EventModel> eventDtoList) {
        return eventDtoList.stream()
                .sorted(Comparator.comparing(EventModel::getTheme))
                .collect(Collectors.toList());
    }

    public List<EventModel> sortByDate(List<EventModel> eventDtoList) {
        return eventDtoList.stream()
                .sorted(Comparator.comparing(EventModel::getTimeSpending))
                .collect(Collectors.toList());
    }

    public List<EventModel> sortByOrganizer(List<EventModel> eventDtoList) {
        return eventDtoList.stream()
                .sorted(Comparator.comparing(EventModel::getOrganizer))
                .collect(Collectors.toList());
    }


}
