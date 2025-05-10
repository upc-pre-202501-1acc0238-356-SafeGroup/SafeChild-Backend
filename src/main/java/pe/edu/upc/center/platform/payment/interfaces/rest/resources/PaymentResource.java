package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;

import java.util.Date;

public record PaymentResource(
        Long id,
        Long userId,
        Reservation reservation,
        Card card,
        Date createdAt) {
}
