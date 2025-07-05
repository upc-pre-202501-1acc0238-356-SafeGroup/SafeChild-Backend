package pe.edu.upc.center.platform.payment.interfaces.rest.resources;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;

public record UpdatePaymentStatusResource(PaymentStatus paymentStatus) {
}
