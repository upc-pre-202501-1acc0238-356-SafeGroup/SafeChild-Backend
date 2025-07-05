package pe.edu.upc.center.platform.schedules.interfaces.rest.transform;

import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleShiftResource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleResourceFromEntityAssembler {
    public static ScheduleResource toResourceFromEntity(Schedule schedule) {

        var scheduleShiftList = schedule.getScheduleShifts().stream()
                .map(slot -> new ScheduleShift(
                        slot.getId(),
                        slot.getSchedule(),
                        slot.getShift(),
                        slot.isAvailable()
                )).collect(Collectors.toList());

        return new ScheduleResource(
                schedule.getId(),
                schedule.getCaregiverId(),
                schedule.getAvailableDate(),
                scheduleShiftList
        );
    }
}

//Long id,
//Long caregiverId,
//LocalDate availableDate,
//List<ScheduleShift> scheduleShifts