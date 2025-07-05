package pe.edu.upc.center.platform.schedules.interfaces.rest.transform;

import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateScheduleDateCommand;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.UpdateScheduleResource;

public class UpdateScheduleCommandFromResourceAssembler {
    public static UpdateScheduleDateCommand toCommandFromResource(Long scheduleId, UpdateScheduleResource resource) {
        return new UpdateScheduleDateCommand(
                scheduleId,
                resource.availableDate()

        );

    }
}
