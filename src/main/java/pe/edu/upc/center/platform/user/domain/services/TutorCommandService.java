package pe.edu.upc.center.platform.user.domain.services;

import pe.edu.upc.center.platform.user.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.user.domain.model.commands.DeleteTutorCommand;
import pe.edu.upc.center.platform.user.domain.model.commands.UpdateTutorCommand;

import java.util.Optional;

public interface TutorCommandService {
    Long handle(CreateTutorCommand command);
    Optional<Tutor> handle(UpdateTutorCommand command);
    void handle(DeleteTutorCommand command);
}
