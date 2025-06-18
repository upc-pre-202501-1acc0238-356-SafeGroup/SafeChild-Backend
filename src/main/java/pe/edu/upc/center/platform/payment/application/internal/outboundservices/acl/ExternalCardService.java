package pe.edu.upc.center.platform.payment.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;
import pe.edu.upc.center.platform.card.interfaces.acl.CardContextFacade;

import java.util.Optional;

@Service("externalcardservicefrompayment")
public class ExternalCardService {
    private final CardContextFacade cardContextFacade;


    public ExternalCardService(CardContextFacade cardContextFacade) {
        this.cardContextFacade = cardContextFacade;
    }

    public Optional<Card> fetchCardById(Long cardId) {
        return this.cardContextFacade.fetchCardById(cardId);
    }
}
