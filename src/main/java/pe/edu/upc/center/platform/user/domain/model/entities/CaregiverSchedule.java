package pe.edu.upc.center.platform.user.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.user.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.user.domain.model.commands.CreateCaregiverScheduleCommand;
import pe.edu.upc.center.platform.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
@Table(name = "caregiver_schedule")
public class CaregiverSchedule extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    @Setter
    @Column(name = "week_day", nullable = false)
    private String weekDay;

    @Setter
    @Column(name = "start_hour", nullable = false)
    private String startHour;

    @Setter
    @Column(name = "end_hour", nullable = false)
    private String endHour;

    public CaregiverSchedule(CreateCaregiverScheduleCommand command, Caregiver caregiver) {
        this.endHour = command.endHour();
        this.startHour = command.startHour();
        this.weekDay = command.weekDay();
        this.caregiver = caregiver;
    }

    public CaregiverSchedule() {

    }
}
