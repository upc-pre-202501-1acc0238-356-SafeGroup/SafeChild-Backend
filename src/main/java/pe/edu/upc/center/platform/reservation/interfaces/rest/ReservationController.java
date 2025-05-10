package pe.edu.upc.center.platform.reservation.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.reservation.application.internal.commandservices.ReservationCommandServiceImpl;
import pe.edu.upc.center.platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.center.platform.reservation.domain.model.commands.UpdateReservationStatusCommand;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByCaregiverQuery;
import pe.edu.upc.center.platform.reservation.domain.model.queries.GetReservationByTutorQuery;
import pe.edu.upc.center.platform.reservation.domain.model.valueobjects.ReservationStatus;
import pe.edu.upc.center.platform.reservation.domain.services.ReservationCommandService;
import pe.edu.upc.center.platform.reservation.domain.services.ReservationQueryService;
import pe.edu.upc.center.platform.reservation.interfaces.rest.resources.CreateReservationResource;
import pe.edu.upc.center.platform.reservation.interfaces.rest.resources.ReservationResource;
import pe.edu.upc.center.platform.reservation.interfaces.rest.transform.CreateReservationCommandFromResourceAssembler;
import pe.edu.upc.center.platform.reservation.interfaces.rest.transform.ReservationResourceFromEntityAssembler;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name="Reservations", description = "Reservation Management Endpoints")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ReservationController {
    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    public ReservationController(ReservationCommandService reservationCommandService, ReservationQueryService reservationQueryService) {
        this.reservationCommandService = reservationCommandService;
        this.reservationQueryService = reservationQueryService;
    }

    @PostMapping
    public ResponseEntity<ReservationResource> createReservation(@RequestBody CreateReservationResource resource) {
        var createReservationCommand = CreateReservationCommandFromResourceAssembler.toCommandFromResource(resource);

        Reservation reservation = reservationCommandService.handle(createReservationCommand);

        return new ResponseEntity<>(ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation), HttpStatus.CREATED);
    }

    @GetMapping("/caregiver/{caregiverId}")
    public ResponseEntity<List<ReservationResource>> getReservationByCaregiverId(@PathVariable Long caregiverId) {
        GetReservationByCaregiverQuery getReservationByCaregiverQuery = new GetReservationByCaregiverQuery(caregiverId);

        List<Reservation> reservations = reservationQueryService.handle(getReservationByCaregiverQuery);

        return new ResponseEntity<>(reservations.stream().map(ReservationResourceFromEntityAssembler::toResourceFromEntity).toList(), HttpStatus.OK);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<ReservationResource>> getReservationByTutorId(@PathVariable Long tutorId) {
        GetReservationByTutorQuery getReservationByTutorQuery = new GetReservationByTutorQuery(tutorId);

        List<Reservation> reservations = reservationQueryService.handle(getReservationByTutorQuery);

        return new ResponseEntity<>(reservations.stream().map(ReservationResourceFromEntityAssembler::toResourceFromEntity).toList(), HttpStatus.OK);
    }

    @PatchMapping("/{reservationId}/status/{status}")
    public ResponseEntity<ReservationResource> updateReservationStatus(@PathVariable Long reservationId, @PathVariable String status) {
        UpdateReservationStatusCommand updateReservationStatusCommand = new UpdateReservationStatusCommand(reservationId, status);
        Reservation reservation = reservationCommandService.handle(updateReservationStatusCommand);
        return new ResponseEntity<>(ReservationResourceFromEntityAssembler.toResourceFromEntity(reservation), HttpStatus.OK);
    }
}
