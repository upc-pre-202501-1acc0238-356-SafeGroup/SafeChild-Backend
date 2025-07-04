package pe.edu.upc.center.platform.schedules.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.schedules.domain.model.aggregates.Schedule;
import pe.edu.upc.center.platform.schedules.domain.model.valueobjects.Shift;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class ScheduleShift {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonBackReference
    Schedule schedule;

    @Enumerated(EnumType.STRING)
    Shift shift;

    boolean available;

    public ScheduleShift(Long id, Schedule schedule, Shift shift, boolean available) {
        this.id = id;
        this.schedule = schedule;
        this.shift = shift;
        this.available = available;
    }

    public ScheduleShift() {

    }
}
