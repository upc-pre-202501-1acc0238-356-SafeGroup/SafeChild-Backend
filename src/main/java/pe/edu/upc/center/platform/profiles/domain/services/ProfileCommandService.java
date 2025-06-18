package pe.edu.upc.center.platform.profiles.domain.services;

import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
  Long handle(CreateProfileCommand command);
  void handle(DeleteProfileCommand command);
}
