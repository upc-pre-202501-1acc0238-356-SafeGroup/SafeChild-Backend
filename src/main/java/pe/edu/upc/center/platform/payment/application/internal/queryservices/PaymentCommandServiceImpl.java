package pe.edu.upc.center.platform.payment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.payment.application.internal.outboundservices.acl.ExternalCardService;
import pe.edu.upc.center.platform.payment.application.internal.outboundservices.acl.ExternalReservationService;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.PaymentRepository;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final ExternalCardService externalCardService;
    private final ExternalReservationService externalReservationService;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository, ExternalReservationService externalReservationService, ExternalCardService externalCardService) {
        this.paymentRepository = paymentRepository;
        this.externalCardService = externalCardService;
        this.externalReservationService = externalReservationService;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {

        Card card = externalCardService.fetchCardById(command.cardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        Reservation reservation = externalReservationService.fetchReservationById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Payment payment = new Payment(command, reservation, card);

        return paymentRepository.save(payment);
    }
}
