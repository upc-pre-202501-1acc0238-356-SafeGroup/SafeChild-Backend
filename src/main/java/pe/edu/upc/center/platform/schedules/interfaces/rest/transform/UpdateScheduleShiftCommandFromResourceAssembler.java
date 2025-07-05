package pe.edu.upc.center.platform.schedules.interfaces.rest.transform;

import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.UpdateScheduleShiftResource;

public class UpdateScheduleShiftCommandFromResourceAssembler {
    public static UpdateShiftAvailabilityCommand toCommandFromResource(Long scheduleAvailabilityId, UpdateScheduleShiftResource resource) {
        return new UpdateShiftAvailabilityCommand(
                scheduleAvailabilityId,
                resource.available()
        );
    }

}



