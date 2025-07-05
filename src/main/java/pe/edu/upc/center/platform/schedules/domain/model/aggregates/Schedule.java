package pe.edu.upc.center.platform.schedules.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import pe.edu.upc.center.platform.schedules.domain.model.commands.CreateScheduleCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.schedules.domain.model.entities.ScheduleShift;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Schedule extends AuditableAbstractAggregateRoot<Schedule> {


    @Column(name = "caregiver_id", nullable = false)
    private Long caregiverId;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;


    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ScheduleShift> scheduleShifts = new ArrayList<>();


//      @JsonManagedReference
//      @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
//      private List<ScheduleAvailability> scheduleAvailabilities = new ArrayList<>();

    public Schedule() {
    }
    public Schedule(Long caregiverId, LocalDate availableDate) {
        this.caregiverId = caregiverId;
        this.availableDate = availableDate;
    }

    public Schedule(CreateScheduleCommand command) {
        this.caregiverId = command.caregiverId();
        this.availableDate = command.availableDate();
    }

    public Schedule updateScheduleInformation(
            LocalDate availableDate
    ) {
        this.availableDate = availableDate;
        return this;
    }


}
