package pe.edu.upc.center.platform.usermanagement.interfaces.rest.transform;

import pe.edu.upc.center.platform.usermanagement.domain.model.commands.UpdateCaregiverScheduleCommand;
import pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources.UpdateCaregiverScheduleResource;

public class UpdateCaregiverScheduleCommandFromResourceAssembler {
    public static UpdateCaregiverScheduleCommand toCommandFromResource(UpdateCaregiverScheduleResource resource) {
        return new UpdateCaregiverScheduleCommand(resource.caregiverScheduleId(), resource.weekDay(), resource.startHour(), resource.endHour());
    }
}
