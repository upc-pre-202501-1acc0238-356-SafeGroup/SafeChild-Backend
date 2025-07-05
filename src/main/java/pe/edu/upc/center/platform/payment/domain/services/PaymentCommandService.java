package pe.edu.upc.center.platform.payment.domain.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentIntentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentStatusCommand;

import java.util.Optional;

public interface PaymentCommandService{
    PaymentIntent handle(CreatePaymentCommand command);
    PaymentIntent handle(UpdatePaymentIntentCommand paymentIntentId) throws StripeException;
    Optional<Payment> handle(UpdatePaymentStatusCommand command);
}

