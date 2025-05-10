package pe.edu.upc.center.platform.card.domain.services;

import pe.edu.upc.center.platform.card.domain.model.commands.CreateCardCommand;
import pe.edu.upc.center.platform.card.domain.model.commands.DeleteCardCommand;
import pe.edu.upc.center.platform.card.domain.model.commands.UpdateCardCommand;
import pe.edu.upc.center.platform.card.domain.model.aggregates.Card;


import java.util.Optional;

public interface CardCommandService {
    Long handle(CreateCardCommand command);
    Optional<Card> handle(UpdateCardCommand command);
    void handle(DeleteCardCommand command);
}

