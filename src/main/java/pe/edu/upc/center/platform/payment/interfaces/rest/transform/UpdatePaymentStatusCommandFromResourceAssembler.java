package pe.edu.upc.center.platform.payment.interfaces.rest.transform;

import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentStatusCommand;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.UpdatePaymentStatusResource;


public class UpdatePaymentStatusCommandFromResourceAssembler {
    public static UpdatePaymentStatusCommand toCommandFromResource(Long id, UpdatePaymentStatusResource resource) {
        return new UpdatePaymentStatusCommand(
                id,
                resource.paymentStatus()
        );
    }
}
