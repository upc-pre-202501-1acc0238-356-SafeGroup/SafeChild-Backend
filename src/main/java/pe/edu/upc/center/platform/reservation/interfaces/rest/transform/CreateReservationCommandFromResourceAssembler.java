package pe.edu.upc.center.platform.reservation.interfaces.rest.transform;

import pe.edu.upc.center.platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.center.platform.reservation.interfaces.rest.resources.CreateReservationResource;

public class CreateReservationCommandFromResourceAssembler {

    public static CreateReservationCommand toCommandFromResource(CreateReservationResource resource) {
        return new CreateReservationCommand(
                resource.caregiverId(),
                resource.tutorId(),
                resource.date(),
                resource.startTime(),
                resource.endTime(),
                resource.totalAmount()
        );
    }
}