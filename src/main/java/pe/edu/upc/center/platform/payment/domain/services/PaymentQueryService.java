package pe.edu.upc.center.platform.payment.domain.services;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentByUserQuery;

import java.util.List;

public interface PaymentQueryService {
    List<Payment> handle(GetPaymentByUserQuery query);
}
