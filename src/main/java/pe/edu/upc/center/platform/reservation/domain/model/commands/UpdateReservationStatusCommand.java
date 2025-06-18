package pe.edu.upc.center.platform.reservation.domain.model.commands;

import pe.edu.upc.center.platform.reservation.domain.model.valueobjects.ReservationStatus;

public record UpdateReservationStatusCommand (Long reservationId, String status) {}

