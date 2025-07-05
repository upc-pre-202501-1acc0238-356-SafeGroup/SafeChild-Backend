package pe.edu.upc.center.platform.schedules.domain.model.commands;

import java.time.LocalDate;

public record CreateScheduleCommand(
        Long caregiverId,
        LocalDate availableDate
) {
}
