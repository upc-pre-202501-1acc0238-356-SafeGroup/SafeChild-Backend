package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;


public record PaymentResource(
        Long id,
        Currency currency,
        PaymentStatus paymentStatus,
        Long amount,
        Long reservation,
        Long caregiverId,
        Long tutorId,
        String stripePaymentId) {
}

