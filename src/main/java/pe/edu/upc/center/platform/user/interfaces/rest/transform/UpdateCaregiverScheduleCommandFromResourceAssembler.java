package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.commands.UpdateCaregiverScheduleCommand;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverScheduleResource;

public class UpdateCaregiverScheduleCommandFromResourceAssembler {
    public static UpdateCaregiverScheduleCommand toCommandFromResource(UpdateCaregiverScheduleResource resource) {
        return new UpdateCaregiverScheduleCommand(resource.caregiverScheduleId(), resource.weekDay(), resource.startHour(), resource.endHour());
    }
}
