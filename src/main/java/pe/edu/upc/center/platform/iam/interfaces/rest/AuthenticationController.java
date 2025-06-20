package pe.edu.upc.center.platform.iam.interfaces.rest;

import pe.edu.upc.center.platform.iam.domain.services.UserCommandService;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.SignInResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.SignUpResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.UserResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

  private final UserCommandService userCommandService;


  public AuthenticationController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  /**
   * Handles the sign-in request.
   * @param signInResource the sign-in request body.
   * @return the authenticated user resource.
   */
  @PostMapping("/sign-in")
  @Operation(summary = "Handles the sign-in request")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User signed in successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error occurred during sign-in process")
})
  public ResponseEntity<AuthenticatedUserResource> signIn(
      @RequestBody SignInResource signInResource) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(
            authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
    return ResponseEntity.ok(authenticatedUserResource);
  }

  /**
   * Handles the sign-up request.
   * @param signUpResource the sign-up request body.
   * @return the created user resource.
   */
  @PostMapping("/sign-up")
  @Operation(summary = "Handles the sign-up request")
  @ApiResponses( value = {
          @ApiResponse(responseCode = "200", description = "User signed up successfully")
          , @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
    var signUpCommand = SignUpCommandFromResourceAssembler
        .toCommandFromResource(signUpResource);
    var user = userCommandService.handle(signUpCommand);
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }

}
