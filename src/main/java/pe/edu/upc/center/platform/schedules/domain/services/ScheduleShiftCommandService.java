package pe.edu.upc.center.platform.schedules.domain.services;

import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;


import java.util.Optional;

public interface ScheduleShiftCommandService {
    Optional<Schedule> handle(UpdateShiftAvailabilityCommand command);

}


