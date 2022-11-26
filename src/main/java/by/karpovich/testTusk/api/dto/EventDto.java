package by.karpovich.testTusk.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    String theme;

    String description;

    String organizer;

    String timeSpending;

    String location;

}
