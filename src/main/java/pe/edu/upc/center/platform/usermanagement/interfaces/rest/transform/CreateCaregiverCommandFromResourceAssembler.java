package pe.edu.upc.center.platform.usermanagement.interfaces.rest.transform;


import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateCaregiverCommand;
import pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources.CreateCaregiverResource;

public class CreateCaregiverCommandFromResourceAssembler {
    public static CreateCaregiverCommand toCommandFromResource(CreateCaregiverResource resource) {
        return new CreateCaregiverCommand(resource.completeName(),
                resource.age(),
                resource.address(),
                resource.caregiverExperience(),
                resource.completedServices(),
                resource.biography(),
                resource.profileImage(),
                resource.farePerHour(),
                resource.districtsScope());
    }
}
