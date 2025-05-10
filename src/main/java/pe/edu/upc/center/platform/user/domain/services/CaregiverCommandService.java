package pe.edu.upc.center.platform.user.domain.services;

import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.user.domain.model.commands.*;

import java.util.Optional;

public interface CaregiverCommandService {
    Caregiver handle(CreateCaregiverCommand command);

    CaregiverSchedule handle(CreateCaregiverScheduleCommand command);

    Optional<Caregiver> handle(UpdateCaregiverBiographyCommand command);

    Optional<Caregiver> handle(UpdateCaregiverPlaceFareCommand command);

    Optional<CaregiverSchedule> handle(UpdateCaregiverScheduleCommand command);
    void handle(DeleteCaregiverScheduleCommand command);
}