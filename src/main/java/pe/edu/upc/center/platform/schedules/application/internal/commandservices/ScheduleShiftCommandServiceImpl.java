package pe.edu.upc.center.platform.schedules.application.internal.commandservices;


import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftCommandService;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.schedules.infrastructure.persistence.jpa.repositories.ScheduleShiftRepository;

import java.util.Optional;

@Service
public class ScheduleShiftCommandServiceImpl implements ScheduleShiftCommandService {

    private final ScheduleShiftRepository scheduleShiftRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleShiftCommandServiceImpl(ScheduleShiftRepository scheduleShiftRepository, ScheduleRepository scheduleRepository) {
        this.scheduleShiftRepository = scheduleShiftRepository;
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public Optional<ScheduleShift> handle(UpdateShiftAvailabilityCommand command) {
        var existsId = scheduleShiftRepository.existsById(command.shiftId());
        if (!existsId) {
            throw new IllegalArgumentException("Availability with id %s not found".formatted(command.shiftId()));
        }



        var result = scheduleShiftRepository.findByScheduleIdAndAvailable(command.scheduleId(), command.shiftId());
        var scheduleAvailabilityToUpdate = result.get();

        // Actualizar disponibilidad del slot
        scheduleAvailabilityToUpdate.(command.free());
        var updatedScheduleAvailability = scheduleAvailabilityRepository.save(scheduleAvailabilityToUpdate);

        // Obtener el Schedule asociado
        Schedule schedule = updatedScheduleAvailability.getSchedule();

        // Verificar si todos los ScheduleAvailability están en false
        boolean allUnavailable = schedule.getScheduleAvailabilities()
                .stream()
                .noneMatch(ScheduleAvailability::isFree); // ninguno es libre

        // Actualizar isAvailable si corresponde
        schedule.setAvailable(!allUnavailable);
        scheduleRepository.save(schedule);

        return Optional.of(schedule);
    }


//    public Reservation handle(UpdateReservationStatusCommand command) {
//        Reservation reservation = reservationRepository.findById(command.reservationId())
//                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
//
//        reservation.setStatus(ReservationStatus.valueOf(command.status()));
//        return reservationRepository.save(reservation);
//    }





}
