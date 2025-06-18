package pe.edu.upc.center.platform.reservation.domain.services;

import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetAllReservationsQuery;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByCaregiverQuery;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationById;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByTutorQuery;


import java.util.List;
import java.util.Optional;

public interface ReservationQueryService {
    List<Reservation> handle(GetReservationByCaregiverQuery query);
    List<Reservation> handle(GetReservationByTutorQuery query);
    Optional<Reservation> handle(GetReservationById query);
}