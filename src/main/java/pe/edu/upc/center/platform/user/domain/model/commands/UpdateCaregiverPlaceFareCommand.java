package pe.edu.upc.center.platform.user.domain.model.commands;

public record UpdateCaregiverPlaceFareCommand(Long caregiverId, Double farePerHour, String districtsScope) {
}
