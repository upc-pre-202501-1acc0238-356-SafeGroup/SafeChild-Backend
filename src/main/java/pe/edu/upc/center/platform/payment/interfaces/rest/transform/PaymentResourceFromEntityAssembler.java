package pe.edu.upc.center.platform.payment.interfaces.rest.transform;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment payment) {
        return new PaymentResource(
                payment.getId(),
                payment.getCurrency(),
                payment.getAmount(),
                payment.getReservationId().reservationId()
        );
    }
}
