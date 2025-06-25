package pe.edu.upc.center.platform.user.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.queries.GetAllCaregiverQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByIdQuery;
import pe.edu.upc.center.platform.user.domain.model.queries.GetCaregiverByLocationQuery;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Districts;
import pe.edu.upc.center.platform.user.domain.services.CaregiverCommandService;
import pe.edu.upc.center.platform.user.domain.services.CaregiverQueryService;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.CaregiverResource;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.CreateCaregiverResource;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverBiographyResource;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverPlaceFareResource;
import pe.edu.upc.center.platform.user.interfaces.rest.transform.*;
import pe.edu.upc.center.platform.user.interfaces.rest.resources.UpdateCaregiverResource;
import pe.edu.upc.center.platform.user.interfaces.rest.transform.UpdateCaregiverCommandFromResourceAssembler;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/caregiver", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Caregiver", description = "Caregiver Management Endpoints")
public class CaregiverController {
    private final CaregiverCommandService caregiverCommandService;
    private final CaregiverQueryService caregiverQueryService;

    public CaregiverController(CaregiverCommandService caregiverCommandService, CaregiverQueryService caregiverQueryService) {
        this.caregiverCommandService = caregiverCommandService;
        this.caregiverQueryService = caregiverQueryService;
    }

    @PostMapping
    ResponseEntity<CaregiverResource> createCaregiver(@RequestBody CreateCaregiverResource resource) {
        var createCaregiverCommand = CreateCaregiverCommandFromResourceAssembler.toCommandFromResource(resource);

        Caregiver caregiver = caregiverCommandService.handle(createCaregiverCommand);

        return new ResponseEntity<>(CaregiverResourceFromEntityAssembler.toResourceFromEntity(caregiver), HttpStatus.CREATED);
    }

    @PutMapping("/{caregiverId}")
    public ResponseEntity<CaregiverResource> updateCaregiver(@PathVariable Long caregiverId, @RequestBody UpdateCaregiverResource resource) {
        var updateCaregiverCommand = UpdateCaregiverCommandFromResourceAssembler.toCommandFromResource(caregiverId, resource);
        var optionalCaregiver = caregiverCommandService.handle(updateCaregiverCommand);

        if (optionalCaregiver.isEmpty())
            return ResponseEntity.badRequest().build();

        var caregiverResource = CaregiverResourceFromEntityAssembler.toResourceFromEntity(optionalCaregiver.get());
        return ResponseEntity.ok(caregiverResource);
    }

    @PatchMapping("/biography")
    ResponseEntity<CaregiverResource> UpdateCaregiverBiography(@RequestBody UpdateCaregiverBiographyResource resource) {
        var updateCaregiverBiographyCommand = UpdateCaregiverBiographyCommandFromResourceAssembler.toCommandFromResource(resource);

        Caregiver caregiver = caregiverCommandService.handle(updateCaregiverBiographyCommand).orElseThrow();

        return new ResponseEntity<>(CaregiverResourceFromEntityAssembler.toResourceFromEntity(caregiver), HttpStatus.OK);
    }

    @PatchMapping("/place-fare")
    ResponseEntity<CaregiverResource> updateCaregiverPlaceFare(@RequestBody UpdateCaregiverPlaceFareResource resource) {
        var updateCaregiverPlaceFareCommand = UpdateCaregiverPlaceFareCommandFromResourceAssembler.toCommandFromResource(resource);

        Caregiver caregiver = caregiverCommandService.handle(updateCaregiverPlaceFareCommand).orElseThrow();

        return new ResponseEntity<>(CaregiverResourceFromEntityAssembler.toResourceFromEntity(caregiver), HttpStatus.OK);
    }


    @GetMapping("/{caregiverId}")
    ResponseEntity<CaregiverResource> getCaregiverById(@PathVariable Long caregiverId) {
        var getCaregiverByIdQuery = new GetCaregiverByIdQuery(caregiverId);

        Caregiver caregiver = caregiverQueryService.handle(getCaregiverByIdQuery)
                .orElseThrow(() -> new IllegalArgumentException("Caregiver not found"));

        return new ResponseEntity<>(CaregiverResourceFromEntityAssembler.toResourceFromEntity(caregiver), HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<List<CaregiverResource>> getAllCaregiver() {
        var getAllCaregiverQuery = new GetAllCaregiverQuery();


        List<CaregiverResource> caregiverResources = caregiverQueryService.handle(getAllCaregiverQuery)
                .stream().map(CaregiverResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(caregiverResources, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<List<CaregiverResource>> getCaregiverByLocation(@RequestParam(required = false)  String district, @RequestParam(required = false)  String sort) {
        var getCaregiverByLocationQuery = new GetCaregiverByLocationQuery(district, sort);

        List<CaregiverResource> caregiverResources = caregiverQueryService.handle(getCaregiverByLocationQuery)
                .stream().map(CaregiverResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(caregiverResources, HttpStatus.OK);
    }
}

