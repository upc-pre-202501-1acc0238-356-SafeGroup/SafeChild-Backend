package pe.edu.upc.center.platform.iam.interfaces.rest;

import pe.edu.upc.center.platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.center.platform.iam.domain.services.UserQueryService;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.UserResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 **/
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

  private final UserQueryService userQueryService;

  public UsersController(UserQueryService userQueryService) {
    this.userQueryService = userQueryService;
  }

  /**
   * This method returns all the users.
   *
   * @return a list of user resources.
   * @see UserResource
   */
  @GetMapping
  @Operation(summary = "Get all users")
  @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Users not found")})
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var getAllUsersQuery = new GetAllUsersQuery();
    var users = userQueryService.handle(getAllUsersQuery);
    var userResources = users.stream()
        .map(UserResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(userResources);
  }

  /**
   * This method returns the user with the given id.
   *
   * @param userId the user id.
   * @return the user resource with the given id
   * @throws RuntimeException if the user is not found
   * @see UserResource
   */
  @GetMapping(value = "/{userId}")
  @Operation(summary = "Get user by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "User not found")})
  public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var user = userQueryService.handle(getUserByIdQuery);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }
}
