package pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Districts;

public record CreateCaregiverResource(String completeName,
                                      Integer age,
                                      String address,
                                      Integer caregiverExperience,
                                      Integer completedServices,
                                      String biography,
                                      String profileImage,
                                      Double farePerHour,
                                      @Schema(name = "districtsScope", description = "Distrito de trabajo", example = "DISTRICT") Districts districtsScope
)
{
}

