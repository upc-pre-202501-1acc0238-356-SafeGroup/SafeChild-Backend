package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.commands.UpdateCaregiverCommand;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverResource;

public class UpdateCaregiverCommandFromResourceAssembler {
    public static UpdateCaregiverCommand toCommandFromResource(Long caregiverId, UpdateCaregiverResource resource) {
        return new UpdateCaregiverCommand(
                caregiverId,
                resource.completeName(),
                resource.age(),
                resource.address(),
                resource.caregiverExperience(),
                resource.completedServices(),
                resource.biography(),
                resource.profileImage(),
                resource.farePerHour(),
                resource.districtsScope(),
                resource.profileId()
        );
    }
}