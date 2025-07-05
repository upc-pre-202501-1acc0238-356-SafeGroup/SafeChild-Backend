package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;

public record CreatePaymentResource(Currency currency, long reservation) {
}

