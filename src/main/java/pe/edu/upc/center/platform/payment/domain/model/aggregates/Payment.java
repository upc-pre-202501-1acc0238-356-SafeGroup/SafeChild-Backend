package pe.edu.upc.center.platform.payment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.ReservationId;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Long amount;

    @Embedded
    @Column(nullable = false)
    private ReservationId reservationId;

    @Column(nullable = false)
    private String stripePaymentId;


    public Payment() {
    }

    public Payment(Currency currency, Long amount, ReservationId reservationId, String stripePaymentId) {
        this.currency = currency;
        this.amount = amount;
        this.reservationId = reservationId;
        this.stripePaymentId = stripePaymentId;
    }
    public Payment(CreatePaymentCommand command) {
        this.currency = command.currency();
        this.amount = command.amount();
        this.reservationId = command.reservationId();
    }
}
