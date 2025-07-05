package pe.edu.upc.center.platform.usermanagement.interfaces.rest.resources;

public record CreateCaregiverScheduleResource(Long caregiverId, String weekDay, String startHour,String endHour) {
}
