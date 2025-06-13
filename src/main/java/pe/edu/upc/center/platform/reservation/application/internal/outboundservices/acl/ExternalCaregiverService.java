package pe.edu.upc.center.platform.reservation.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.interfaces.acl.CaregiverContextFacade;

import java.util.Optional;

@Service("externalcaregiverservicefromreservation")
public class ExternalCaregiverService {
    private final CaregiverContextFacade caregiverContextFacade;


    public ExternalCaregiverService(CaregiverContextFacade caregiverContextFacade) {
        this.caregiverContextFacade = caregiverContextFacade;
    }

    public Optional<Caregiver> fetchCaregiverById(Long caregiverId) {
        return this.caregiverContextFacade.fetchCaregiverById(caregiverId);
    }
}