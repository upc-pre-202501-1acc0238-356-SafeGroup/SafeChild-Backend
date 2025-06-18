package pe.edu.upc.center.platform.profiles.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

  private final ProfileRepository profileRepository;

  public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public Long handle(CreateProfileCommand command) {
    var profile = new Profile(command);
    try {
      this.profileRepository.save(profile);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while saving profile: " + e.getMessage());
    }
    return profile.getId();
  }

  @Override
  public void handle(DeleteProfileCommand command) {
    // If the profile does not exist, throw an exception
    if (!this.profileRepository.existsById(command.profileId())) {
      throw new IllegalArgumentException("Profile with id " + command.profileId() + " does not exist");
    }

    // Try to delete the profile, if an error occurs, throw an exception
    try {
      this.profileRepository.deleteById(command.profileId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Error while deleting profile: " + e.getMessage());
    }
  }
}
