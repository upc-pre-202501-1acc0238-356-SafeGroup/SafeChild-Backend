package pe.edu.upc.center.platform.schedules.domain.model.commands;

import java.time.LocalDate;

public record UpdateScheduleDateCommand(Long scheduleId,
                                        LocalDate availableDate
                                    ) {
}
