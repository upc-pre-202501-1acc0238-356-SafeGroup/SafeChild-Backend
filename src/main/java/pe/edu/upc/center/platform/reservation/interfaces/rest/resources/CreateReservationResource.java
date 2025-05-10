package pe.edu.upc.center.platform.reservation.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record CreateReservationResource(
        Long caregiverId,
        Long tutorId,
        LocalDateTime date,
        String startTime,
        String endTime,
        double totalAmount
) {}