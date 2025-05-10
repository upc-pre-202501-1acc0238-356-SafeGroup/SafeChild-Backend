package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.commands.UpdateTutorCommand;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.TutorResource;

public class UpdateTutorCommandFromResourceAssembler {
    public static UpdateTutorCommand toCommandFromResource(Long profileId, TutorResource resource) {
        return new UpdateTutorCommand(profileId, resource.fullName(), resource.email(), resource.doc(), resource.password(),resource.number(), resource.street(), resource.district(),resource.role());
    }
}
