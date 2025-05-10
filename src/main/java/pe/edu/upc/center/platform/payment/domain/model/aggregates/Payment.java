package pe.edu.upc.center.platform.payment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Table(name = "payment")
public class Payment extends AuditableAbstractAggregateRoot<Payment> {


    @Column(nullable = false)
    private Long userId;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;


    public Payment() {
    }

    public Payment(CreatePaymentCommand command, Reservation reservation, Card card) {
        this.userId = command.userId();
        this.reservation = reservation;
        this.card = card;
    }
}
