package pe.edu.upc.center.platform.user.interfaces.rest.transform;

import pe.edu.upc.center.platform.user.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.TutorResource;

public class TutorResourceFromEntityAssembler {
    public static TutorResource toResourceFromEntity(Tutor entity) {
        return new TutorResource(entity.getId(), entity.getFullName(), entity.getEmail(), entity.getDocument(), entity.getPassword(), entity.getPhone(), entity.getAddressStreet(), entity.getAddressDistrict(), entity.getRole(), entity.getProfileId());
    }
}
