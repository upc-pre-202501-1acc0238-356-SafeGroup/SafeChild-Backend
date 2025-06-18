package pe.edu.upc.center.platform.reservation.interfaces.rest.transform;

import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.interfaces.rest.resources.ReservationResource;

import java.text.SimpleDateFormat;

public class ReservationResourceFromEntityAssembler {

    public static ReservationResource toResourceFromEntity(Reservation entity) {
        return new ReservationResource(
                entity.getId(),
                entity.getCaregiverId(),
                entity.getTutorId(),
                entity.getDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus().name(),
                entity.getTotalAmount()
        );
    }
}