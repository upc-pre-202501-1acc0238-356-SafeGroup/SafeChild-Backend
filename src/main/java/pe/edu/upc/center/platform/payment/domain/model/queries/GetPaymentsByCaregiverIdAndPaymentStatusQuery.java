package pe.edu.upc.center.platform.payment.domain.model.queries;

import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;

public record GetPaymentsByCaregiverIdAndPaymentStatusQuery(Long caregiverId, PaymentStatus paymentStatus) {

}
