package pe.edu.upc.center.platform.payment.domain.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;

public interface PaymentCommandService{
    Payment handle(CreatePaymentCommand command);
    PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException;
}

