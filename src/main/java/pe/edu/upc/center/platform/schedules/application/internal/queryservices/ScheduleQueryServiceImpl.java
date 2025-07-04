package pe.edu.upc.center.platform.schedules.application.internal.queryservices;


import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.queries.*;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleQueryService;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleQueryServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> handle(GetAllSchedulesQuery query) {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<Schedule> handle(GetByIdQuery query) {
        return scheduleRepository.findById(query.id());
    }

    @Override
    public List<Schedule> handle(GetBySchedulesByCaregiverIdQuery query) {
        return scheduleRepository.findScheduleByCaregiverId(query.caregiverId());
    }


}
