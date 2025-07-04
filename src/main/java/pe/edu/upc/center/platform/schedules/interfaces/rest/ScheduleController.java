package pe.edu.upc.center.platform.schedules.interfaces.rest;


import pe.edu.upc.center.platform.schedules.domain.model.commands.DeleteScheduleCommand;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetAllSchedulesQuery;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetByIdQuery;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetBySchedulesByCaregiverIdQuery;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleCommandService;
import pe.edu.upc.center.platform.schedules.domain.services.ScheduleQueryService;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.CreateScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.UpdateScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.CreateScheduleCommandFromResourceAssembler;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.ScheduleResourceFromEntityAssembler;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.UpdateScheduleCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/schedules", produces = APPLICATION_JSON_VALUE)
@Tag(name="Schedules", description = "Management of caregiver schedules")
public class ScheduleController {
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;
    public ScheduleController(ScheduleCommandService scheduleCommandService, ScheduleQueryService scheduleQueryService) {
        this.scheduleCommandService = scheduleCommandService;
        this.scheduleQueryService = scheduleQueryService;
    }


    @PostMapping
    @Operation(summary = "Create Schedule", description = "Creates a new schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Schedule created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")})
    public ResponseEntity<ScheduleResource> createSchedule(@RequestBody CreateScheduleResource resource) {
        var createScheduleCommand = CreateScheduleCommandFromResourceAssembler.toCommandFromResource(resource);
        var scheduleId = this.scheduleCommandService.handle(createScheduleCommand);
        if (scheduleId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var getScheduleByIdQuery = new GetByIdQuery(scheduleId.get().getId());
        var optionalSchedule = this.scheduleQueryService.handle(getScheduleByIdQuery);
        var scheduleResource = ScheduleResourceFromEntityAssembler.toResourceFromEntity(optionalSchedule.get());
        return ResponseEntity.ok(scheduleResource);
    }

    @PutMapping("/{scheduleId}")
    @Operation(summary = "Update a schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")})
    public ResponseEntity<ScheduleResource> updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleResource resource) {
        var updateScheduleCommand = UpdateScheduleCommandFromResourceAssembler.toCommandFromResource(scheduleId, resource);
        var scheduleOptional = this.scheduleCommandService.handle(updateScheduleCommand);
        if (scheduleOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updatedEntity = scheduleOptional.get();
        var scheduleResource = ScheduleResourceFromEntityAssembler.toResourceFromEntity(updatedEntity);
        return ResponseEntity.ok(scheduleResource);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "Delete a schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule deleted"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")})
    public ResponseEntity<?> deleteSchedule(@PathVariable Long scheduleId) {
        var deleteCommand = new DeleteScheduleCommand(scheduleId);
        this.scheduleCommandService.handle(deleteCommand);
        return ResponseEntity.ok("Schedule with given id successfully deleted");
    }

    @GetMapping("/{scheduleId}")
    @Operation(summary = "Get a schedule by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule found"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")})
    public ResponseEntity<ScheduleResource> getScheduleById(@PathVariable Long scheduleId) {
        var getScheduleByIdQuery = new GetByIdQuery(scheduleId);
        var optionalSchedule = this.scheduleQueryService.handle(getScheduleByIdQuery);
        if (optionalSchedule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var scheduleResource = ScheduleResourceFromEntityAssembler.toResourceFromEntity(optionalSchedule.get());
        return ResponseEntity.ok(scheduleResource);
    }



    @GetMapping
    @Operation(summary = "Get all schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedules retrieved successfully")})
    public ResponseEntity<List<ScheduleResource>> getAllSchedules() {
        var getAllSchedulesQuery = new GetAllSchedulesQuery();
        var schedules = this.scheduleQueryService.handle(getAllSchedulesQuery);
        var scheduleResources = schedules.stream()
                .map(ScheduleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(scheduleResources);
    }

    @GetMapping("/caregiver/{caregiverId}")
        @Operation(summary = "Get schedules by caregiver id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Schedules found"),
                @ApiResponse(responseCode = "404", description = "Schedules not found")})
        public ResponseEntity<List<ScheduleResource>> getSchedulesByCaregiverId(@PathVariable Long caregiverId) {
            var getByCaregiverIdQuery = new GetBySchedulesByCaregiverIdQuery(caregiverId);
            var schedules = this.scheduleQueryService.handle(getByCaregiverIdQuery);
            if (schedules.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var scheduleResources = schedules.stream()
                    .map(ScheduleResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(scheduleResources);
        }

}
