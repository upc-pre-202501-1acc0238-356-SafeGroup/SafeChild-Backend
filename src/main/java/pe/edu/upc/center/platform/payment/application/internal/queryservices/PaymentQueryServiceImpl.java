package pe.edu.upc.center.platform.payment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentByUserQuery;
import pe.edu.upc.center.platform.payment.domain.services.PaymentQueryService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.PaymentRepository;

import java.util.List;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetPaymentByUserQuery query) {
        return paymentRepository.findAllByUserId(query.userId())
                .stream().toList();
    }
}