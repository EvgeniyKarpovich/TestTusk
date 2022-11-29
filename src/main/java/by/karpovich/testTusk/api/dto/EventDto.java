package by.karpovich.testTusk.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotBlank(message = "Enter theme")
    String theme;

    @NotBlank(message = "Enter description")
    String description;

    @NotBlank(message = "Enter organizer")
    String organizer;

    @NotBlank(message = "Enter timeSpending")
    String timeSpending;

    @NotBlank(message = "Enter location")
    String location;

}
