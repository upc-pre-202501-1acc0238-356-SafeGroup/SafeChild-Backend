package pe.edu.upc.center.platform.reservation.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.reservation.domain.model.commands.CreateReservationCommand;
import pe.edu.upc.center.platform.reservation.domain.model.valueobjects.ReservationStatus;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation extends AuditableAbstractAggregateRoot<Reservation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long caregiverId;

    @Column(nullable = false)
    private Long tutorId;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    public Reservation(CreateReservationCommand command) {
        this.caregiverId = command.caregiverId();
        this.tutorId = command.tutorId();
        this.date = command.date();
        this.startTime = command.startTime();
        this.endTime = command.endTime();
        this.totalAmount = command.totalAmount();
    }

    public Reservation() {}

}
