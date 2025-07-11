package pe.edu.upc.center.platform.iam.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.interfaces.acl.ProfilesContextFacade;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.usermanagement.interfaces.acl.CaregiverContextFacade;
import pe.edu.upc.center.platform.usermanagement.interfaces.acl.TutorContextFacade;

import java.util.Optional;

@Service
public class ExternalUserProfilesService {

    private final TutorContextFacade tutorContextFacade;
    private final CaregiverContextFacade caregiverContextFacade;

    public ExternalUserProfilesService(TutorContextFacade tutorContextFacade, CaregiverContextFacade caregiverContextFacade) {
        this.tutorContextFacade = tutorContextFacade;
        this.caregiverContextFacade = caregiverContextFacade;
    }


    public Optional<Caregiver> createCaregiverExternal(long id) {
        return this.caregiverContextFacade.createCaregiverById(id);
    }

    public Optional<Tutor> createTutorExternal(long id){
        return this.tutorContextFacade.createTutorById(id);
    }

}
