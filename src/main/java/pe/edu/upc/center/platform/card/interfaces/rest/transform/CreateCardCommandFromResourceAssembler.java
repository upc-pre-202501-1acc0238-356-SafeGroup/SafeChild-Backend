package pe.edu.upc.center.platform.card.interfaces.rest.transform;

import pe.edu.upc.center.platform.card.domain.model.commands.CreateCardCommand;
import pe.edu.upc.center.platform.card.interfaces.rest.resources.CreateCardResource;

public class CreateCardCommandFromResourceAssembler {
    public static CreateCardCommand toCommandFromResource(CreateCardResource resource) {
        return new CreateCardCommand(resource.profileId(), resource.number(),resource.holder(),resource.year(),resource.month(),resource.code());
    }
}

