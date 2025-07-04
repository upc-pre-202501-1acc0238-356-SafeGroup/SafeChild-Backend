package pe.edu.upc.center.platform.schedules.application.internal.commandservices;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftCommandService;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleShiftRepository;

import java.util.Optional;

@Service
public class ScheduleShiftCommandServiceImpl implements ScheduleShiftCommandService {

    private final ScheduleShiftRepository scheduleShiftRepository;

    public ScheduleShiftCommandServiceImpl(ScheduleShiftRepository scheduleShiftRepository) {
        this.scheduleShiftRepository = scheduleShiftRepository;
    }


    @Override
    public Optional<ScheduleShift> handle(UpdateShiftAvailabilityCommand command) {

        Optional<ScheduleShift> optionalScheduleShift = scheduleShiftRepository.findById(command.shiftId());

        if (optionalScheduleShift.isEmpty()) {
            throw new IllegalArgumentException("Schedule shift with id %s not found".formatted(command.shiftId()));
        }

        ScheduleShift scheduleShift = optionalScheduleShift.get();

        scheduleShift.setAvailable(command.available());

        scheduleShiftRepository.save(scheduleShift);

        return Optional.of(scheduleShift);
    }


}
