package pe.edu.upc.center.platform.user.domain.model.commands;

public record UpdateCaregiverBiographyCommand(Long caregiverId, String biography) {
}
