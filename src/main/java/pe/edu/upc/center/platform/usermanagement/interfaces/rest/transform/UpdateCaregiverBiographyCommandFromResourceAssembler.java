package pe.edu.upc.center.platform.usermanagement.interfaces.rest.transform;

import pe.edu.upc.center.platform.usermanagement.domain.model.commands.UpdateCaregiverBiographyCommand;
import pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources.UpdateCaregiverBiographyResource;

public class UpdateCaregiverBiographyCommandFromResourceAssembler {
    public static UpdateCaregiverBiographyCommand toCommandFromResource(UpdateCaregiverBiographyResource resource) {
        return new UpdateCaregiverBiographyCommand(resource.caregiverId(), resource.biography());
    }
}
