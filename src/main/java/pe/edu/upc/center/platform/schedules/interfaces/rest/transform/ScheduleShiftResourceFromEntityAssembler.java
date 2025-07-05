package pe.edu.upc.center.platform.schedules.interfaces.rest.transform;

import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.Shift;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleShiftResource;


public class ScheduleShiftResourceFromEntityAssembler {
        public static ScheduleShiftResource toResourcefromEntity(ScheduleShift scheduleShift) {
       //     var timeSlot = scheduleAvailability.getTimeSlot();

            return new ScheduleShiftResource(
                    scheduleShift.getId(),
                    scheduleShift.getSchedule().getId(),
                    scheduleShift.getShift(),
                    scheduleShift.isAvailable()

            );
        }
}