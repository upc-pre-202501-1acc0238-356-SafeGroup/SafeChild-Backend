package pe.edu.upc.center.platform.schedules.domain.model.entities;

import jakarta.persistence.*;
import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.Shift;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class ScheduleShift extends AuditableAbstractAggregateRoot<ScheduleShift> {


    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule scheduleId;

    @Enumerated(EnumType.STRING)
    Shift shift;

    boolean available;

    public ScheduleShift() {
    }

}
