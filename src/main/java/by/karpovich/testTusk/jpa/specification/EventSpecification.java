package by.karpovich.testTusk.jpa.specification;

import by.karpovich.testTusk.api.searchCriteriaDto.EventCriteriaDto;
import by.karpovich.testTusk.jpa.model.EventModel;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

    public static Specification<EventModel> findByTheme(String theme) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.lower(root.get("theme")), "%" + theme.toLowerCase() + "%");
    }

    public static Specification<EventModel> findByOrganizer(String organizer) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.
                like(criteriaBuilder.lower(root.get("organizer")), "%" + organizer.toLowerCase() + "%");
    }

    public static Specification<EventModel> defaultSpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), 0);
    }

    public static Specification<EventModel> createFromCriteria(EventCriteriaDto criteriaDto) {
        Specification<EventModel> eventSpecification = defaultSpecification();

        if (criteriaDto.getTheme() != null) {
            eventSpecification = eventSpecification.and(findByTheme(criteriaDto.getTheme()));
        }
        if (criteriaDto.getOrganizer() != null) {
            eventSpecification = eventSpecification.and(findByOrganizer(criteriaDto.getOrganizer()));
        }
        return eventSpecification;
    }
}
