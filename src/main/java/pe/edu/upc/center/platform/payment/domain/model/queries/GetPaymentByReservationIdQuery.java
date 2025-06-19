package pe.edu.upc.center.platform.payment.domain.model.queries;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.ReservationId;

public record GetPaymentByReservationIdQuery(ReservationId reservationId) {
}