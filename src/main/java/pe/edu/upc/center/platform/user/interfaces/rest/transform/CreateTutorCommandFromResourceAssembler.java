package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.CreateTutorResource;

public class CreateTutorCommandFromResourceAssembler {
    public static CreateTutorCommand toCommandFromResource(CreateTutorResource resource) {
        return new CreateTutorCommand(resource.fullName(), resource.email(), resource.doc(), resource.password(),resource.number(), resource.street(), resource.district(),resource.role());
    }
}
