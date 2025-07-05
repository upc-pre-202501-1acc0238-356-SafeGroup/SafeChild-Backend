package pe.edu.upc.center.platform.schedules.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateScheduleResource(LocalDate availableDate) {
}
