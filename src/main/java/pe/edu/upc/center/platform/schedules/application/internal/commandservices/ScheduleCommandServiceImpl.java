package pe.edu.upc.center.platform.schedules.application.internal.commandservices;


import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.CreateScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.DeleteScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateScheduleDateCommand;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.Shift;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleCommandService;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleShiftRepository scheduleShiftRepository;

    public ScheduleCommandServiceImpl(ScheduleRepository scheduleRepository, ScheduleShiftRepository scheduleShiftRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleShiftRepository = scheduleShiftRepository;
    }


    @Override
    public Optional<Schedule> handle(CreateScheduleCommand command) {
        var schedule = new Schedule(
                command.caregiverId(),
                command.availableDate()
        );

        schedule = scheduleRepository.save(schedule);

        Schedule objSchedule = schedule;

        List<ScheduleShift> shifts = Arrays.stream(Shift.values())
                .map(shift -> {
                    ScheduleShift scheduleShift = new ScheduleShift();
                    scheduleShift.setShift(shift);
                    scheduleShift.setAvailable(true);
                    scheduleShift.setSchedule(objSchedule); // vincular el shift al schedule
                    return scheduleShift;
                })
                .collect(Collectors.toList());

        schedule.setScheduleShifts(shifts);

        schedule = scheduleRepository.save(schedule);

        return Optional.of(schedule);
    }

    @Override
    public Void handle(DeleteScheduleCommand command) {


        if (!scheduleRepository.existsById(command.scheduleId())) {
            throw new IllegalArgumentException("Schedule with id %s not found".formatted(command.scheduleId()));
        }
        try {
            scheduleRepository.deleteById(command.scheduleId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting schedule: %s".formatted(e.getMessage()));
        }
        return null;
    }

    @Transactional
    @Override
    public Optional<Schedule> handle(UpdateScheduleDateCommand command) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(command.scheduleId());

        if (optionalSchedule.isEmpty()) {
            throw new IllegalArgumentException("Schedule with id %s not found".formatted(command.scheduleId()));
        }

        Schedule scheduleToUpdate = optionalSchedule.get();

        scheduleToUpdate.updateScheduleInformation(
                command.availableDate()
        );

        Schedule updatedSchedule = scheduleRepository.save(scheduleToUpdate);

        return Optional.of(updatedSchedule);
    }

}

