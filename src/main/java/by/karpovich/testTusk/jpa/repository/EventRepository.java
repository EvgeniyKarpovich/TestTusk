package by.karpovich.testTusk.jpa.repository;

import by.karpovich.testTusk.jpa.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventModel, Long>,
        PagingAndSortingRepository<EventModel, Long>,
        JpaSpecificationExecutor<EventModel> {

    Optional<EventModel> findByOrganizerAndLocation(String org, String loc);
}
