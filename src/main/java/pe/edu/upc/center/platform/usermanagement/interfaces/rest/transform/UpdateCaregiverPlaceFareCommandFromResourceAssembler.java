package pe.edu.upc.center.platform.usermanagement.interfaces.rest.transform;

import pe.edu.upc.center.platform.usermanagement.domain.model.commands.UpdateCaregiverPlaceFareCommand;
import pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources.UpdateCaregiverPlaceFareResource;

public class UpdateCaregiverPlaceFareCommandFromResourceAssembler {
    public static UpdateCaregiverPlaceFareCommand toCommandFromResource(UpdateCaregiverPlaceFareResource resource) {
        return new UpdateCaregiverPlaceFareCommand(resource.caregiverId(), resource.farePerHour(), resource.districtsScope());
    }
}
