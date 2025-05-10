package pe.edu.upc.center.platform.card.domain.services;

import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.card.domain.model.queries.GetAllCardsQuery;
import pe.edu.upc.center.platform.card.domain.model.queries.GetCardByCardNumberQuery;
import pe.edu.upc.center.platform.card.domain.model.queries.GetCardByIdQuery;
import pe.edu.upc.center.platform.card.domain.model.queries.GetCardByProfileId;

import java.util.List;
import java.util.Optional;

public interface CardQueryService {
    List<Card> handle(GetAllCardsQuery query);
    Optional<Card> handle(GetCardByCardNumberQuery query);
    Optional<Card> handle(GetCardByIdQuery query);
    Optional<List<Card>> handle(GetCardByProfileId query);
}
