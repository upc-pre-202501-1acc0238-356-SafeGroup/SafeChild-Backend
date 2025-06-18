package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.commands.UpdateCaregiverBiographyCommand;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverBiographyResource;

public class UpdateCaregiverBiographyCommandFromResourceAssembler {
    public static UpdateCaregiverBiographyCommand toCommandFromResource(UpdateCaregiverBiographyResource resource) {
        return new UpdateCaregiverBiographyCommand(resource.caregiverId(), resource.biography());
    }
}
