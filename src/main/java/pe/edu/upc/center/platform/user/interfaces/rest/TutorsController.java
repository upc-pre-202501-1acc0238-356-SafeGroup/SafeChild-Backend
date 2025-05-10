package pe.edu.upc.center.platform.user.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.user.domain.model.commands.DeleteTutorCommand;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllTutorsQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetTutorByIdQuery;
import pe.edu.upc.center.platform.user.domain.services.TutorCommandService;
import pe.edu.upc.center.platform.user.domain.services.TutorQueryService;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.CreateTutorResource;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.TutorResource;
import pe.edu.upc.center.platform.user.interfaces.rest.transform.CreateTutorCommandFromResourceAssembler;
import pe.edu.upc.center.platform.user.interfaces.rest.transform.TutorResourceFromEntityAssembler;
import pe.edu.upc.center.platform.user.interfaces.rest.transform.UpdateTutorCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/tutors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag( name = "Tutors", description = "Tutors Management Endpoints")
public class TutorsController {
    private final TutorQueryService tutorQueryService;
    private final TutorCommandService tutorCommandService;

    public TutorsController(TutorQueryService tutorQueryService, TutorCommandService tutorCommandService) {
        this.tutorQueryService = tutorQueryService;
        this.tutorCommandService = tutorCommandService;
    }

    @PostMapping
    public ResponseEntity<TutorResource> createTutor(@RequestBody CreateTutorResource resource) {

        var createTutorCommand = CreateTutorCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var tutorId = this.tutorCommandService.handle(createTutorCommand);

        if (tutorId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getTutorByIdQuery = new GetTutorByIdQuery(tutorId);
        var optionalTutor = this.tutorQueryService.handle(getTutorByIdQuery);

        var tutorResource = TutorResourceFromEntityAssembler.toResourceFromEntity(optionalTutor.get());
        return new ResponseEntity<>(tutorResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TutorResource>> getAllTutors() {
        var getAllTutorsQuery = new GetAllTutorsQuery();
        var tutors = this.tutorQueryService.handle(getAllTutorsQuery);
        var tutorResources = tutors.stream()
                .map(TutorResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tutorResources);
    }

    @GetMapping("/{tutorId}")
    public ResponseEntity<TutorResource> getTutorById(@PathVariable Long tutorId) {
        var getTutorByIdQuery = new GetTutorByIdQuery(tutorId);
        var optionalTutor = this.tutorQueryService.handle(getTutorByIdQuery);
        if (optionalTutor.isEmpty())
            return ResponseEntity.badRequest().build();
        var tutorResource = TutorResourceFromEntityAssembler.toResourceFromEntity(optionalTutor.get());
        return ResponseEntity.ok(tutorResource);
    }

    @PutMapping("/{tutorId}")
    public ResponseEntity<TutorResource> updateTutor(@PathVariable Long tutorId, @RequestBody TutorResource resource) {
        var updateTutorCommand = UpdateTutorCommandFromResourceAssembler.toCommandFromResource(tutorId, resource);
        var optionalTutor = this.tutorCommandService.handle(updateTutorCommand);

        if (optionalTutor.isEmpty())
            return ResponseEntity.badRequest().build();
        var tutorResource = TutorResourceFromEntityAssembler.toResourceFromEntity(optionalTutor.get());
        return ResponseEntity.ok(tutorResource);
    }

    @DeleteMapping("/{tutorId}")
    public ResponseEntity<?> deleteTutor(@PathVariable Long tutorId) {
        var deleteTutorCommand = new DeleteTutorCommand(tutorId);
        this.tutorCommandService.handle(deleteTutorCommand);
        return ResponseEntity.noContent().build();
    }
}
