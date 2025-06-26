package pe.edu.upc.center.platform.usermanagement.domain.model.commands;

import  pe.edu.upc.center.platform.usermanagement.domain.model.valueobjects.Districts;

public record CreateCaregiverCommand(
        String completeName,
        Integer age,
        String address,
        Integer caregiverExperience,
        Integer completedServices,
        String biography,
        String profileImage,
        Double farePerHour,
        Districts districtsScope) {
}
