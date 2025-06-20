package pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.ReservationId;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReservationId(ReservationId reservationId);
    Optional<Payment> findByStripePaymentId(String stripePaymentId);
    boolean existsByStripePaymentId(String stripePaymentId);
}
