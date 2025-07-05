package pe.edu.upc.center.platform.payment.domain.model.commands;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;

public record UpdatePaymentStatusCommand(Long id, PaymentStatus paymentStatus) {
}
