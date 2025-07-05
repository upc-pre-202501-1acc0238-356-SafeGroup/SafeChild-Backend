package pe.edu.upc.center.platform.payment.application.internal.commandservices;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentIntentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentStatusCommand;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories.PaymentRepository;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.infrastructure.persistence.jpa.repositories.ReservationRepository;
import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateScheduleDateCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class CommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    @Value("${stripe.api.key.test}")
    private String key;

    public CommandServiceImpl(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public PaymentIntent handle(CreatePaymentCommand command) {
        Stripe.apiKey = key;

        Reservation reservation = reservationRepository.findById(command.reservation())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        long amount = (long)reservation.getTotalAmount();
        amount = amount * 100;
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(command.currency().name())
                        //TODO: ACTIVAR PARA USAR PAGO AUTOMÁTICO DESDE FLUTTER
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
//                                        .build()
//                        )
                        .addPaymentMethodType("card")
                        .build();

        try {

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            paymentIntent = PaymentIntent.retrieve(paymentIntent.getId());

            Payment payment = new Payment(command);
            payment.setCurrency(command.currency());
            payment.setAmount(amount);
            payment.setReservation(reservation);
            payment.setStripePaymentId(paymentIntent.getId());
            payment.setPaymentStatus(PaymentStatus.valueOf(paymentIntent.getStatus().toUpperCase()));

            paymentRepository.save(payment);

            return paymentIntent;
        } catch (StripeException e) {
            throw new IllegalArgumentException("Error creating payment intent: " + e.getMessage(), e);
        }
    }

    @Override
    public PaymentIntent handle(UpdatePaymentIntentCommand paymentIntentId) throws StripeException {
        Stripe.apiKey = key;

        PaymentIntent confirmedPaymentIntent = PaymentIntent.retrieve(paymentIntentId.paymentIntentId());
        if (confirmedPaymentIntent == null) {
            throw new IllegalArgumentException("Payment intent with id %s not found".formatted(paymentIntentId));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        confirmedPaymentIntent.confirm(params);

        //TODO: implementar un estado para Payment, quitar comms y agregar la
        // lógica para actualizar el estado del pago en la base de datos
//        Optional<Payment> paymentOptional = paymentRepository.findByStripePaymentId(paymentIntentId);
//        if (paymentOptional.isPresent()) {
//            Payment payment = paymentOptional.get();
//            paymentRepository.save(payment);
//        } else {
//            throw new IllegalArgumentException("Payment with Stripe ID %s not found in the database".formatted(paymentIntentId));
//        }



        return confirmedPaymentIntent;
    }

    @Override
    public Optional<Payment> handle(UpdatePaymentStatusCommand command) {
        Optional<Payment> optionalPayment = paymentRepository.findPaymentById(command.id());

        if (optionalPayment.isEmpty()) {
            throw new IllegalArgumentException("Payment with id %s not found".formatted(command.id()));
        }

        Payment paymentToUpdate = optionalPayment.get();

        String paymentStatus = String.valueOf(command.paymentStatus());

        PaymentStatus updatedPaymentStatus = PaymentStatus.valueOf(paymentStatus.toUpperCase());

        paymentToUpdate.setPaymentStatus(updatedPaymentStatus);

        Payment updatedPayment = paymentRepository.save(paymentToUpdate);

        return Optional.of(updatedPayment);
    }


}
