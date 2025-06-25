package pe.edu.upc.center.platform.payment.domain.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentIntentCommand;

public interface PaymentCommandService{
    PaymentIntent handle(CreatePaymentCommand command);
    PaymentIntent handle(UpdatePaymentIntentCommand paymentIntentId) throws StripeException;
}

