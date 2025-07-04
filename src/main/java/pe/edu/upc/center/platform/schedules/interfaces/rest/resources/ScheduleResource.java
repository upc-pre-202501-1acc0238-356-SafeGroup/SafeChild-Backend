package pe.edu.upc.center.platform.schedules.interfaces.rest.resources;


import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ScheduleResource(
        Long id,
        Long caregiverId,
        LocalDate availableDate,
        List<ScheduleShift> scheduleShifts
) {
}
