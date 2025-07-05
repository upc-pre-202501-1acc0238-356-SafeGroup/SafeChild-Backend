package pe.edu.upc.center.platform.usermanagement.domain.model.commands;

public record UpdateCaregiverScheduleCommand(Long caregiverScheduleId, String weekDay, String startHour, String endHour) {
}
