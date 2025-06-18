package pe.edu.upc.center.platform.payment.domain.model.commands;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;

public record CreatePaymentCommand(Currency currency, Double amount, Long reservationId, String stripePaymentId) {
}

