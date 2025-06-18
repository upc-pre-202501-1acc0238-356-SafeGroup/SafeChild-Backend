package pe.edu.upc.center.platform.messaging.interfaces.rest.resources;

import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;

import java.time.LocalDateTime;

public record MessageResource(Long id, User sender, User receiver, String content, LocalDateTime timestamp) {
}
