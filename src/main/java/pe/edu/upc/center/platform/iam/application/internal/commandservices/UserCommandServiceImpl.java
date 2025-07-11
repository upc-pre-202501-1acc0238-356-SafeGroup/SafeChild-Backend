package pe.edu.upc.center.platform.iam.application.internal.commandservices;


import pe.edu.upc.center.platform.iam.application.internal.outboundservices.acl.ExternalUserProfilesService;
import pe.edu.upc.center.platform.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.center.platform.iam.application.internal.outboundservices.tokens.TokenService;
import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.center.platform.iam.domain.model.entities.Role;
import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.center.platform.iam.domain.services.UserCommandService;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.usermanagement.interfaces.acl.CaregiverContextFacade;
import pe.edu.upc.center.platform.usermanagement.interfaces.acl.TutorContextFacade;

import java.util.Optional;

import static pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles.CAREGIVER;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final TokenService tokenService;

  private final RoleRepository roleRepository;
  private final ExternalUserProfilesService externalUserProfilesService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository, ExternalUserProfilesService externalUserProfilesService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.externalUserProfilesService = externalUserProfilesService;
    }


    /**
   * Handle the sign-in command
   * <p>
   *     This method handles the {@link SignInCommand} command and returns the user and the token.
   * </p>
   * @param command the sign-in command containing the username and password
   * @return and optional containing the user matching the username and the generated token
   * @throws RuntimeException if the user is not found or the password is invalid
   */
  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var user = userRepository.findByUsername(command.username());
    if (user.isEmpty())
      throw new RuntimeException("User not found");
    if (!hashingService.matches(command.password(), user.get().getPassword()))
      throw new RuntimeException("Invalid password");

    var token = tokenService.generateToken(user.get().getUsername());
    return Optional.of(ImmutablePair.of(user.get(), token));
  }

  /**
   * Handle the sign-up command
   * <p>
   *     This method handles the {@link SignUpCommand} command and returns the user.
   * </p>
   * @param command the sign-up command containing the username and password
   * @return the created user
   */
  @Override
  public Optional<User> handle(SignUpCommand command) {
    if (userRepository.existsByUsername(command.username()))
      throw new RuntimeException("Username already exists");

    var roles = command.roles().stream()
            .map(role -> roleRepository.findByName(role.getName())
                    .orElseThrow(() -> new RuntimeException("Role name not found")))
            .toList();

    System.out.println("Roles recibidos en SignUpCommand: " + command.roles());

    var user = new User(command.username(), hashingService.encode(command.password()), roles);
    var savedUser = userRepository.save(user);

    var iamId = savedUser.getId();
    System.out.println("Roles recibidos en SignUpCommand: " + command.roles());

    try {
      for (var role : savedUser.getRoles()) {
        if (Roles.TUTOR.equals(role.getName())) {
          externalUserProfilesService.createTutorExternal(iamId);

        } else if (Roles.CAREGIVER.equals(role.getName())) {
          externalUserProfilesService.createCaregiverExternal(iamId);
        } else {
          throw new RuntimeException("Role not supported: " + role.getName());
        }
      }
  } catch (Exception e) {
    throw new RuntimeException("Error al invocar el servicio de perfiles externos de usuario: " + e.getMessage(), e);
  }

    System.out.println("Roles recibidos en SignUpCommand: " + command.roles());

    return userRepository.findByUsername(command.username());
  }

}
