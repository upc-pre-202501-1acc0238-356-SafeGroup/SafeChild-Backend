package pe.edu.upc.center.platform.usermanagement.domain.services;

import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.usermanagement.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.*;

import java.util.Optional;

public interface CaregiverCommandService {
    Caregiver handle(CreateCaregiverCommand command);

    CaregiverSchedule handle(CreateCaregiverScheduleCommand command);

    Optional<Caregiver> handle(UpdateCaregiverBiographyCommand command);

    Optional<Caregiver> handle(UpdateCaregiverPlaceFareCommand command);

    Optional<CaregiverSchedule> handle(UpdateCaregiverScheduleCommand command);

    void handle(DeleteCaregiverScheduleCommand command);

    Optional<Caregiver> handle(UpdateCaregiverCommand command);
}