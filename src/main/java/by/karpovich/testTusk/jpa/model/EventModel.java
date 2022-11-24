package by.karpovich.testTusk.jpa.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Events")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "theme", nullable = false)
    String theme;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "organizer", nullable = false)
    String organizer;

    @Column(name = "time_spending", nullable = false)
    Instant timeSpending;

    @Column(name = "location", nullable = false)
    String location;

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    Instant dateOfChange;

}
