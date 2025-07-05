package pe.edu.upc.center.platform.payment.interfaces.rest.transform;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment payment) {
        return new PaymentResource(
                payment.getId(),
                payment.getCurrency(),
                payment.getPaymentStatus(),
                payment.getAmount(),
                payment.getReservation().getId(),
                payment.getReservation().getCaregiverId(),
                payment.getReservation().getTutorId(),
                payment.getStripePaymentId()

        );
    }
}
