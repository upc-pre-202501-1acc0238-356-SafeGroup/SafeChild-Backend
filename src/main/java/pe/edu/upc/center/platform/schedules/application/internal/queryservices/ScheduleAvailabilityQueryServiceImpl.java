package pe.edu.upc.center.platform.schedules.application.internal.queryservices;


import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetShiftByScheduleIdAndShiftAvailabilityQuery;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftQueryService;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleShiftRepository;

import java.util.List;

@Service
public class ScheduleAvailabilityQueryServiceImpl implements ScheduleShiftQueryService {

    private final ScheduleShiftRepository scheduleShiftRepository;

    public ScheduleAvailabilityQueryServiceImpl(ScheduleShiftRepository scheduleShiftRepository) {
        this.scheduleShiftRepository = scheduleShiftRepository;
    }

    @Override
    public List<ScheduleShift> handle(GetShiftByScheduleIdAndShiftAvailabilityQuery query) {
        return this.scheduleShiftRepository.findByScheduleIdAndAvailable(query.scheduleId(), query.availability());
    }
}
