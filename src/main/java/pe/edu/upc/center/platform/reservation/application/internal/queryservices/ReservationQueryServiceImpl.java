package pe.edu.upc.center.platform.reservation.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetAllReservationsQuery;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByCaregiverQuery;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationById;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByTutorQuery;
import pe.edu.upc.center.platform.reservation.domain.services.ReservationQueryService;
import pe.edu.upc.center.platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ReservationQueryServiceImpl implements ReservationQueryService {
    private final ReservationRepository reservationRepository;

    public ReservationQueryServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> handle(GetReservationByCaregiverQuery query) {
        return reservationRepository.findByCaregiverId(query.caregiverId());
    }

    @Override
    public List<Reservation> handle(GetReservationByTutorQuery query) {
        return reservationRepository.findByTutorId(query.tutorId());
    }

    @Override
    public Optional<Reservation> handle(GetReservationById query) {
        return reservationRepository.findById(query.reservationId());
    }
}