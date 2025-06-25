package pe.edu.upc.center.platform.payment.domain.model.commands;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.ReservationId;

public record CreatePaymentCommand(Currency currency, Long amount, ReservationId reservationId) {
}

