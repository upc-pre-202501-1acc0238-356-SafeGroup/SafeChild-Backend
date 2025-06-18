package pe.edu.upc.center.platform.card.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.card.domain.model.queries.GetCardByIdQuery;
import pe.edu.upc.center.platform.card.domain.services.CardQueryService;

import java.util.Optional;

@Service
public class CardContextFacade {
    private final CardQueryService cardQueryService;


    public CardContextFacade(CardQueryService cardQueryService) {
        this.cardQueryService = cardQueryService;
    }

    public Optional<Card> fetchCardById(Long cardId) {
        var getCardByIdQuery = new GetCardByIdQuery(cardId);
        return cardQueryService.handle(getCardByIdQuery);
    }
}