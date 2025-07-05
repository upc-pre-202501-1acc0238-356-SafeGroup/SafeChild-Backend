package pe.edu.upc.center.platform.schedules.domain.model.commands;

public record UpdateShiftAvailabilityCommand(

        Long shiftId, boolean available
) {
}
