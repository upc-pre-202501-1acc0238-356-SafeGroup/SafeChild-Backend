package pe.edu.upc.center.platform.card.domain.model.queries;

import pe.edu.upc.center.platform.card.domain.model.valueobjects.CardNumber;

public record GetCardByCardNumberQuery(CardNumber cardNumber) {
}

