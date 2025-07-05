package pe.edu.upc.center.platform.messaging.interfaces.rest.resources;

import java.time.LocalDateTime;

public record MessageResource(Long id, pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor sender, pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver receiver, String content, LocalDateTime timestamp) {
}
