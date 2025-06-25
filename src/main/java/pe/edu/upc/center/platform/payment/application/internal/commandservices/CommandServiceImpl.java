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
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories.PaymentRepository;

import java.util.HashMap;
import java.util.Map;


@Service
public class CommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.key.test}")
    private String key;

    public CommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentIntent handle(CreatePaymentCommand command) {
        Stripe.apiKey = key;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(command.amount())
                        .setCurrency(command.currency().name())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Payment payment = new Payment(command);
            payment.setCurrency(command.currency());
            payment.setAmount(command.amount());
            payment.setReservationId(command.reservationId());
            payment.setStripePaymentId(paymentIntent.getId());

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

}
