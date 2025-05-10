package pe.edu.upc.center.platform.user.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByIdQuery;
import pe.edu.upc.center.platform.user.domain.services.CaregiverCommandService;
import pe.edu.upc.center.platform.user.domain.services.CaregiverQueryService;

import java.util.Optional;

@Service
public class CaregiverContextFacade {
    private final CaregiverCommandService caregiverCommandService;
    private final CaregiverQueryService caregiverQueryService;


    public CaregiverContextFacade(CaregiverCommandService caregiverCommandService, CaregiverQueryService caregiverQueryService) {
        this.caregiverCommandService = caregiverCommandService;
        this.caregiverQueryService = caregiverQueryService;
    }

    public Optional<Caregiver> fetchCaregiverById(Long caregiverId) {
        var getCaregiverByIdQuery = new GetCaregiverByIdQuery(caregiverId);
        return caregiverQueryService.handle(getCaregiverByIdQuery);
    }
}