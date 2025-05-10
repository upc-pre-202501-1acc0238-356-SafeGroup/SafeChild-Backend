package pe.edu.upc.center.platform.user.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.interfaces.acl.ProfilesContextFacade;

import java.util.Optional;

@Service
public class ExternalProfileService {

    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }


    public Optional<Long> createProfile() {
        var profileId = this.profilesContextFacade.createProfile();
        if (profileId.equals(0L))
            return Optional.empty();
        return Optional.of(profileId);
    }


}
