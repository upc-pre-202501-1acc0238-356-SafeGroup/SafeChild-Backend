package pe.edu.upc.center.platform.schedules.domain.services;

import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ScheduleQueryService {


    List<Schedule> handle(GetAllSchedulesQuery query);
    Optional<Schedule> handle(GetByIdQuery query);
    List<Schedule> handle(GetBySchedulesByCaregiverIdQuery query);

}
