package pe.edu.upc.center.platform.schedules.interfaces.rest.transform;

import pe.edu.upc.center.platform.schedules.domain.model.commands.CreateScheduleCommand;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.CreateScheduleResource;

public class CreateScheduleCommandFromResourceAssembler {
    public static CreateScheduleCommand toCommandFromResource(CreateScheduleResource resource) {
        return new CreateScheduleCommand(
                resource.caregiverId(),
                resource.availableDate()
        );

    }
}

