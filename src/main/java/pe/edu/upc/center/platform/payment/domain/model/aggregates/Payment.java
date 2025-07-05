package pe.edu.upc.center.platform.payment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.center.platform.payment.domain.model.commands.CreatePaymentCommand;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.Currency;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;
import pe.edu.upc.center.platform.reservation.domain.model.aggregates.Reservation;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private String stripePaymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    public Payment(Currency currency, Long amount, Reservation reservationId, String stripePaymentId, PaymentStatus paymentStatus) {
        this.currency = currency;
        this.amount = amount;
        this.reservation = reservationId;
        this.stripePaymentId = stripePaymentId;
        this.paymentStatus = paymentStatus;
    }
    public Payment(CreatePaymentCommand command) {
        this.currency = command.currency();
    }
}
