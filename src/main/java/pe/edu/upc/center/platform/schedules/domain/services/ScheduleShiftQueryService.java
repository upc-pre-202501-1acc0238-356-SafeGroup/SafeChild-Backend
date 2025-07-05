package pe.edu.upc.center.platform.schedules.domain.services;

import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetShiftByScheduleIdAndShiftAvailabilityQuery;

import java.util.List;

public interface ScheduleShiftQueryService {

    List<ScheduleShift> handle(GetShiftByScheduleIdAndShiftAvailabilityQuery query);
}
