package by.karpovich.testTusk.api.controller;

import by.karpovich.testTusk.api.dto.EventDto;
import by.karpovich.testTusk.api.searchCriteriaDto.EventCriteriaDto;
import by.karpovich.testTusk.service.EventService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody EventDto eventDto) {
        eventService.saveEvent(eventDto);

        return new ResponseEntity<>("Event saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody EventDto eventDto,
                                    @PathVariable("id") Long id) {
        EventDto update = eventService.update(id, eventDto);

        if (update == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);

        return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        EventDto byId = eventService.findById(id);

        if (byId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> all = eventService.findAllSortByDate(page, size);

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
    }

    @GetMapping("/search/byDate/{date}")
    public ResponseEntity<?> findAllDate(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size, @PathVariable("date") String date) {
        Map<String, Object> all = eventService.findAllByDate(page, size, date);

        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
    }

    @GetMapping("/search/byCriteria")
    public ResponseEntity<?> findByCriteria(EventCriteriaDto eventCriteriaDto) {
        List<EventDto> allByCriteria = eventService.findAllByCriteria(eventCriteriaDto);

        if (allByCriteria.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allByCriteria, HttpStatus.OK);
    }

}
