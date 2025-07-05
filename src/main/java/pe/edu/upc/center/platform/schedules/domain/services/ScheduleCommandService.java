package pe.edu.upc.center.platform.schedules.domain.services;

import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.CreateScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.DeleteScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateScheduleDateCommand;

import java.util.Optional;

public interface ScheduleCommandService {
    Optional<Schedule> handle(CreateScheduleCommand command);
    Void handle(DeleteScheduleCommand command);
    Optional<Schedule> handle(UpdateScheduleDateCommand command);
}
