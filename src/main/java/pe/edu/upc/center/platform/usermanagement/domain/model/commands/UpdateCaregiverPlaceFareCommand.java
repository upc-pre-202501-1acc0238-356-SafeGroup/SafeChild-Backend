package pe.edu.upc.center.platform.usermanagement.domain.model.commands;

public record UpdateCaregiverPlaceFareCommand(Long caregiverId, Double farePerHour, String districtsScope) {
}
