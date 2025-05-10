package pe.edu.upc.center.platform.reservation.domain.services;

import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.commands.CancelReservationCommand;
import pe.edu.upc.center.platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.center.platform.reservation.domain.model.commands.UpdateReservationStatusCommand;

public interface ReservationCommandService {
    Reservation handle(CreateReservationCommand command);
    Reservation handle(UpdateReservationStatusCommand command);
}