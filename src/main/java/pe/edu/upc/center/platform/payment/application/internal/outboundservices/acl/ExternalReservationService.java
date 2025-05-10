package pe.edu.upc.center.platform.payment.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.interfaces.acl.ReservationContextFacade;

import java.util.Optional;

@Service("externalreservationservicefrompayment")
public class ExternalReservationService {
    private final ReservationContextFacade reservationContextFacade;


    public ExternalReservationService(ReservationContextFacade reservationContextFacade) {
        this.reservationContextFacade = reservationContextFacade;
    }

    public Optional<Reservation> fetchReservationById(Long reservationId) {
        return this.reservationContextFacade.fetchReservationById(reservationId);
    }
}