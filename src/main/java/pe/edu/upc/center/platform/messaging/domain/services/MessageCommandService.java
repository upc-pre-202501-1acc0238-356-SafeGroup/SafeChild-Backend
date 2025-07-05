package pe.edu.upc.center.platform.messaging.domain.services;

import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.commands.CreateMessageCommand;

public interface MessageCommandService {
    Message handle(CreateMessageCommand command);
}
