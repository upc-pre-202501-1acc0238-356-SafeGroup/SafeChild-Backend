package pe.edu.upc.center.platform.schedules.interfaces.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.center.platform.schedules.domain.model.commands.UpdateShiftAvailabilityCommand;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetShiftByScheduleIdAndShiftAvailabilityQuery;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftCommandService;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleShiftQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleShiftResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.UpdateScheduleShiftResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.ScheduleShiftResourceFromEntityAssembler;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.UpdateScheduleShiftCommandFromResourceAssembler;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/shifts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Schedule Shifts", description = "Management of caregiver hours")
public class ScheduleShiftController {

    private final ScheduleShiftCommandService scheduleShiftCommandService;
    private final ScheduleShiftQueryService scheduleShiftQueryService;

    public ScheduleShiftController(ScheduleShiftCommandService scheduleShiftCommandService, ScheduleShiftQueryService scheduleShiftQueryService) {
        this.scheduleShiftCommandService = scheduleShiftCommandService;
        this.scheduleShiftQueryService = scheduleShiftQueryService;
    }


    @PutMapping("/availability/{id}")
    @Operation(summary = "put availability by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado")})
    public ResponseEntity<ScheduleShiftResource> updateScheduleShift(@PathVariable Long id, @RequestBody UpdateScheduleShiftResource resource) {
        var updateScheduleShiftAvailabilityCommand = UpdateScheduleShiftCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var scheduleOptional = this.scheduleShiftCommandService.handle(updateScheduleShiftAvailabilityCommand);
        if (scheduleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updatedEntity = scheduleOptional.get();
       var scheduleAvailability = ScheduleShiftResourceFromEntityAssembler.toResourcefromEntity(updatedEntity);
        return ResponseEntity.ok(scheduleAvailability);

    }

    @GetMapping("/schedule/{scheduleId}/availability/{availability}")
    @Operation(summary = "get schedule shifts by schedule id and availability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "shifts encontrados"),
            @ApiResponse(responseCode = "404", description = "Schedule no encontrado")})
    public ResponseEntity<List<ScheduleShiftResource>> getScheduleShiftsIdAndAvailable(@PathVariable Long scheduleId, @PathVariable boolean availability) {
        var query = new GetShiftByScheduleIdAndShiftAvailabilityQuery(scheduleId,availability);
        var scheduleAvailabilities = scheduleShiftQueryService.handle(query);

        if (scheduleAvailabilities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = scheduleAvailabilities.stream()
                .map(ScheduleShiftResourceFromEntityAssembler::toResourcefromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }


}

