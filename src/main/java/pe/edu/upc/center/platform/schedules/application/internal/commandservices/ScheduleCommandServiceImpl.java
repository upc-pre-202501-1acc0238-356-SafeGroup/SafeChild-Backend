package pe.edu.upc.center.platform.schedules.application.internal.commandservices;


import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.CreateScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.DeleteScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateScheduleDateCommand;
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
    private final ScheduleShiftRepository timeSlotRepository;
    private final ScheduleAvailabilityRepository scheduleAvailabilityRepository;

    public ScheduleCommandServiceImpl(
            ScheduleRepository scheduleRepository,
            ScheduleShiftRepository timeSlotRepository,
            ScheduleAvailabilityRepository scheduleAvailabilityRepository) {
        this.scheduleRepository = scheduleRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.scheduleAvailabilityRepository = scheduleAvailabilityRepository;
    }


    @Override
    public Optional<Schedule> handle(CreateScheduleCommand command) {
        var schedule = new Schedule(
                command.advisorId(),
                command.availableDate(),
                command.startTime(),
                command.endTime(),
                command.isAvailable()
        );


//        TechnicianDTO technicianExternal;
//        try {
//            technicianExternal = userClient.getTechnicianById(command.advisorId());
//            if (technicianExternal.roles().contains(Roles.ROLE_CLIENT))
//                throw new IllegalArgumentException("The user with id %s is not a technician".formatted(command.advisorId()));
//        }catch (FeignException e){
//            String err = switch (e.status()){
//                case 402, 404 -> "Technician with id %s not found".formatted(command.advisorId());
//                default -> "Unexpected value: " + e.status();
//            };
//            throw new IllegalArgumentException(err,e);
//        }


        // Guardar el Schedule para obtener su ID
        schedule = scheduleRepository.save(schedule);

        // Crear las entradas en ScheduleAvailability
        List<TimeSlot> matchingSlots = timeSlotRepository.findAllWithinRange(command.startTime(), command.endTime());

        matchingSlots = matchingSlots.stream()
                .filter(slot -> slot.getStartTime().isBefore(slot.getEndTime()))
                .toList();

        if (matchingSlots.isEmpty()) {
            throw new IllegalArgumentException("No valid time slots found between %s and %s"
                    .formatted(command.startTime(), command.endTime()));
        }

        List<ScheduleAvailability> scheduleAvailabilities = new ArrayList<>();
        for (TimeSlot slot : matchingSlots) {
            ScheduleAvailabilityKey availabilityKey = new ScheduleAvailabilityKey();
            availabilityKey.setScheduleId(schedule.getId()); //TODO: LLAMAR schedule id por acl
            availabilityKey.setSlotId(slot.getId());

            ScheduleAvailability availability = new ScheduleAvailability();
            availability.setId(availability.getId()); // Usa la clave compuesta
            availability.setSchedule(schedule);
            availability.setTimeSlot(slot);
            availability.setFree(true);

            scheduleAvailabilities.add(availability);
        }

        // Guardar las ScheduleAvailabilities
        scheduleAvailabilityRepository.saveAll(scheduleAvailabilities);

        // Actualizar el campo isAvailable del Schedule
        boolean isScheduleAvailable = scheduleAvailabilities.stream()
                .anyMatch(ScheduleAvailability::isFree);
        schedule.setAvailable(isScheduleAvailable);

        // Guardar el Schedule actualizado
        scheduleRepository.save(schedule);

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

        // Actualizar información base del Schedule
        scheduleToUpdate.updateScheduleInformation(
                command.advisorId(),
                command.availableDate(),
                command.startTime(),
                command.endTime(),
                command.isAvailable() // El estado de available ahora depende directamente del input
        );

        // Obtener los TimeSlot dentro del rango actualizado
        List<TimeSlot> matchingSlots = timeSlotRepository.findAllWithinRange(command.startTime(), command.endTime());
        matchingSlots = matchingSlots.stream()
                .filter(slot -> slot.getStartTime().isBefore(slot.getEndTime()))
                .toList();

        if (matchingSlots.isEmpty()) {
            throw new IllegalArgumentException("No valid time slots found between %s and %s"
                    .formatted(command.startTime(), command.endTime()));
        }

        // Map para saber qué ScheduleAvailability ya existen
        Map<Long, ScheduleAvailability> existingAvailabilities = scheduleToUpdate.getScheduleAvailabilities()
                .stream()
                .collect(Collectors.toMap(sa -> sa.getTimeSlot().getId(), sa -> sa));

        List<ScheduleAvailability> updatedAvailabilities = new ArrayList<>();

        for (TimeSlot slot : matchingSlots) {
            ScheduleAvailability availability = existingAvailabilities.get(slot.getId());

            if (availability == null) {
                // No existía, se crea
                availability = new ScheduleAvailability();
                availability.setSchedule(scheduleToUpdate);
                availability.setTimeSlot(slot);
                availability.setFree(true); // o puedes usar alguna lógica para definir esto
            }

            updatedAvailabilities.add(availability);
        }

        // Reemplazar todas las availabilities del Schedule
        scheduleToUpdate.getScheduleAvailabilities().clear();
        scheduleToUpdate.getScheduleAvailabilities().addAll(updatedAvailabilities);

        // Guardar el Schedule (propaga los cambios por cascada)
        Schedule updatedSchedule = scheduleRepository.save(scheduleToUpdate);

        return Optional.of(updatedSchedule);
    }

}

