package pe.edu.upc.center.platform.user.domain.services;

import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllCaregiverQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllCaregiverScheduleByCaregiverIdQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByIdQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByLocationQuery;

import java.util.List;
import java.util.Optional;

public interface CaregiverQueryService {
    List<Caregiver> handle(GetAllCaregiverQuery query);
    List<CaregiverSchedule> handle(GetAllCaregiverScheduleByCaregiverIdQuery query);
    Optional<Caregiver> handle(GetCaregiverByIdQuery query);
    List<Caregiver> handle(GetCaregiverByLocationQuery query);
}

