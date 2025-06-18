package pe.edu.upc.center.platform.payment.domain.services;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
}

