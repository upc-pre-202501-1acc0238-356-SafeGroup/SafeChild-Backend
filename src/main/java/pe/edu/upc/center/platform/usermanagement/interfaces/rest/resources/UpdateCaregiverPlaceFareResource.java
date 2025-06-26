package pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources;

public record UpdateCaregiverPlaceFareResource(Long caregiverId, Double farePerHour, String districtsScope) {
}

