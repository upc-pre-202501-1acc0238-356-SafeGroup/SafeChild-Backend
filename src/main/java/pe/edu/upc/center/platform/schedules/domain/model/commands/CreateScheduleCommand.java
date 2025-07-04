package pe.edu.upc.center.platform.schedules.domain.model.commands;

public record CreateScheduleCommand(
        Long caregiverId,
        boolean isAvailable
) {
}
