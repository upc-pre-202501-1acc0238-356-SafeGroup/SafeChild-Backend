package pe.edu.upc.center.platform.schedules.interfaces.rest.resources;


import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.Shift;

public record ScheduleShiftResource(
        Long id,
        Long scheduleId,
        Shift shift,
        boolean available
) {}
