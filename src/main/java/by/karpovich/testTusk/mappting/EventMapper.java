package by.karpovich.testTusk.mappting;

import by.karpovich.testTusk.api.dto.EventDto;
import by.karpovich.testTusk.jpa.model.EventModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateOfCreation", ignore = true)
    @Mapping(target = "dateOfChange", ignore = true)
    EventModel mapFromDto(EventDto dto);

    EventDto mapFromEntity(EventModel model);

    List<EventModel> mapFromListDto(List<EventDto> dtoList);

    List<EventDto> mapFromListEntity(List<EventModel> modelList);

    default String map(Instant instant) {
        return instant == null ? null :
                instant.atOffset(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
