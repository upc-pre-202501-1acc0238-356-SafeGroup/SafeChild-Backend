package pe.edu.upc.center.platform.reservation.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.card.application.internal.outboundservices.acl.ExternalCaregiverService;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.center.platform.reservation.domain.model.commands.UpdateReservationStatusCommand;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.valueobjects.ReservationStatus;
import pe.edu.upc.center.platform.reservation.domain.services.ReservationCommandService;
import pe.edu.upc.center.platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;


@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {
    private final ReservationRepository reservationRepository;
    private final ExternalCaregiverService externalCaregiverService;

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository, ExternalCaregiverService externalCaregiverService) {
        this.reservationRepository = reservationRepository;
        this.externalCaregiverService = externalCaregiverService;
    }

    public Reservation handle(CreateReservationCommand command) {

        Caregiver caregiver = externalCaregiverService.fetchCaregiverById(command.caregiverId())
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        Reservation reservation = new Reservation(command);
        return reservationRepository.save(reservation);
    }

    public Reservation handle(UpdateReservationStatusCommand command) {
        Reservation reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.setStatus(ReservationStatus.valueOf(command.status()));
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }
}

