package pe.edu.upc.center.platform.reservation.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ReservationResource(
        Long id,
        Long caregiverId,
        Long tutorId,
        LocalDateTime date,
        String startTime,
        String endTime,
        String status,
        double totalAmount
) {}