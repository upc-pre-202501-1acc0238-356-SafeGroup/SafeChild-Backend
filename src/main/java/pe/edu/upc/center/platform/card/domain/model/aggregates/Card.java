package pe.edu.upc.center.platform.card.domain.model.aggregates;

import jakarta.persistence.*;
import pe.edu.upc.center.platform.card.domain.model.commands.CreateCardCommand;
import pe.edu.upc.center.platform.card.domain.model.commands.UpdateCardCommand;
import pe.edu.upc.center.platform.card.domain.model.valueobjects.CardHolder;
import pe.edu.upc.center.platform.card.domain.model.valueobjects.Cvv;
import pe.edu.upc.center.platform.card.domain.model.valueobjects.ExpirationDate;
import pe.edu.upc.center.platform.card.domain.model.valueobjects.CardNumber;

import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cards")
public class Card extends AuditableAbstractAggregateRoot<Card> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cardNumber", column = @Column(name = "card_number", length = 16,nullable = false)),
    })
    private CardNumber cardNumber;

    @Column(nullable = false)
    private Long profileId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cardHolder", column = @Column(name = "card_holder", length = 50,nullable = false)),
    })
    private CardHolder cardHolder;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "year", column = @Column(name = "expiration_year", nullable = false)),
            @AttributeOverride(name = "month", column = @Column(name = "expiration_month", nullable = false)),
    })
    private ExpirationDate expirationDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="cvv", column = @Column(name = "cvv",length = 3, nullable = false)),
    })
    private Cvv cvv;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public Card() {

    }

    public Card(String number, String holder,int year, int month, String code) {
        this.cardNumber = new CardNumber(number);
        this.cardHolder = new CardHolder(holder);
        this.expirationDate = new ExpirationDate(year, month);
        this.cvv = new Cvv(code);
    }

    public void updateCardNumber(String number) {
        this.cardNumber = new CardNumber(number);
    }

    public String getCardNumber(){
        return cardNumber.number();
    }
    public void updateCardHolder(String holder) {
        this.cardHolder = new CardHolder(holder);
    }

    public String getCardHolder(){
        return cardHolder.holder();
    }

    public void updateExpirationDate(int year, int month) {
        this.expirationDate = new ExpirationDate(year, month);
    }

    public int getExpirationYear() {
        return expirationDate.year();
    }

    public int getExpirationMonth() {
        return expirationDate.month();
    }
    public void updateCvv(String code) {
        this.cvv = new Cvv(code);
    }
    public String getCvv(){
        return cvv.code();
    }


    public Card(CreateCardCommand command) {
        this.cardNumber= new CardNumber(command.number());
        this.cardHolder= new CardHolder(command.holder());
        this.expirationDate=new ExpirationDate(command.year(), command.month());
        this.cvv=new Cvv(command.code());
        this.profileId = command.userId();
    }

    public Card updateInformation(UpdateCardCommand command) {
        this.cardNumber= new CardNumber(command.number());
        this.cardHolder= new CardHolder(command.holder());
        this.expirationDate=new ExpirationDate(command.year(), command.month());
        this.cvv=new Cvv(command.code());
        return this;
    }


}