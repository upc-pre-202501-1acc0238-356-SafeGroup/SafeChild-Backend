package pe.edu.upc.center.platform.usermanagement.domain.model.commands;

public record UpdateCaregiverCommand(
        Long caregiverId,
        String completeName,
        Integer age,
        String address,
        Integer caregiverExperience,
        Integer completedServices,
        String biography,
        String profileImage,
        Double farePerHour,
        String districtsScope,
        Long profileId) {
}