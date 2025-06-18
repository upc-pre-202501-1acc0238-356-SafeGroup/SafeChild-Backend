package pe.edu.upc.center.platform.payment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetAllPaymentsQuery;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentByReservationIdQuery;
import pe.edu.upc.center.platform.payment.domain.services.PaymentQueryService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements PaymentQueryService {

    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> handle(GetPaymentByReservationIdQuery query) {
        return this.paymentRepository.findByReservationId(query.reservationId());
    }

    @Override
    public List<Payment> handle(GetAllPaymentsQuery query) {
        return this.paymentRepository.findAll();
    }
}
