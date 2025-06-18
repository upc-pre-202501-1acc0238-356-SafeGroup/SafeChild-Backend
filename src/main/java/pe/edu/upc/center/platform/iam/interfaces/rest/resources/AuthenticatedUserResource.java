package pe.edu.upc.center.platform.iam.interfaces.rest.resources;


import java.util.List;

public record AuthenticatedUserResource(Long id, String username, List<String> roles, String token) {
}
