package pe.edu.upc.center.platform.user.domain.model.commands;

public record CreateCaregiverScheduleCommand(Long caregiverId, String weekDay, String startHour,String endHour) {
}

