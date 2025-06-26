package pe.edu.upc.center.platform.usermanagement.interfaces.rest.transform;

import pe.edu.upc.center.platform.usermanagement.domain.model.entities.CaregiverSchedule;
import pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources.CaregiverScheduleResource;

public class CaregiverScheduleResourceFromEntityAssembler {
    public static CaregiverScheduleResource toResourceFromEntity(CaregiverSchedule entity) {
        return new CaregiverScheduleResource(entity.getId(), entity.getWeekDay(), entity.getStartHour(), entity.getEndHour());
    }
}
