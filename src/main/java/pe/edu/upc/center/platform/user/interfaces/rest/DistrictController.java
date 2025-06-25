package pe.edu.upc.center.platform.user.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.user.domain.model.valueobjects.Districts;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
@Tag(name = "Districts", description = "Districts Endpoint")
public class DistrictController {

    @GetMapping
    public ResponseEntity<List<String>> getAllDistricts() {
        List<String> districts = Arrays.stream(Districts.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(districts);
    }
}