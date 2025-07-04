package pe.edu.upc.center.platform.schedules.application.internal.queryservices;


import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleAvailabilityQueryServiceImpl implements ScheduleShiftQueryService {

    private final ScheduleAvailabilityRepository scheduleAvailabilityRepository;

    public ScheduleAvailabilityQueryServiceImpl(ScheduleAvailabilityRepository scheduleAvailabilityRepository) {
        this.scheduleAvailabilityRepository = scheduleAvailabilityRepository;
    }

    @Override
    public List<ScheduleAvailability> handle(GetTimeSlotsByScheduleIdAndAvailableQuery query) {
        return scheduleAvailabilityRepository.findByScheduleIdAndFree(query.scheduleId(), query.available());
    }
}
