package pe.edu.upc.center.platform.usermanagement.domain.services;

import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorByIdCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.CreateTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.DeleteTutorCommand;
import pe.edu.upc.center.platform.usermanagement.domain.model.commands.UpdateTutorCommand;

import java.util.Optional;

public interface TutorCommandService {
    Long handle(CreateTutorCommand command);
    Optional<Tutor> handle(UpdateTutorCommand command);
    Optional<Tutor> handle(CreateTutorByIdCommand command);
    void handle(DeleteTutorCommand command);
}
