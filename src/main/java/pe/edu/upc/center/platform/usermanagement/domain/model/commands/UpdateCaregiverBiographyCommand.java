package pe.edu.upc.center.platform.usermanagement.domain.model.commands;

public record UpdateCaregiverBiographyCommand(Long caregiverId, String biography) {
}
