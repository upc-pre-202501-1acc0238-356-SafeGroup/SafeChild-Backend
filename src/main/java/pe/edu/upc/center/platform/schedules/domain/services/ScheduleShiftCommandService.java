package pe.edu.upc.center.platform.schedules.domain.services;

import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;


import java.util.Optional;

public interface ScheduleShiftCommandService {
    Optional<ScheduleShift> handle(UpdateShiftAvailabilityCommand command);

}


