package pe.edu.upc.center.platform.reservation.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationById;
import pe.edu.upc.center.platform.reservation.domain.services.ReservationQueryService;

import java.util.Optional;

@Service
public class ReservationContextFacade {
    private final ReservationQueryService reservationQueryService;


    public ReservationContextFacade(ReservationQueryService reservationQueryService) {
        this.reservationQueryService = reservationQueryService;
    }

    public Optional<Reservation> fetchReservationById(Long reservationId) {
        var getReservationById = new GetReservationById(reservationId);
        return reservationQueryService.handle(getReservationById);
    }
}
