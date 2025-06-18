package pe.edu.upc.center.platform.payment.domain.services;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentByReservationIdQuery;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(GetPaymentByReservationIdQuery query);
    List<Payment> handle(GetAllPaymentsQuery query);

}
