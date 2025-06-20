package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;


public record PaymentResource(Long paymentId, Currency currency, Long amount, Long reservationId) {
}
