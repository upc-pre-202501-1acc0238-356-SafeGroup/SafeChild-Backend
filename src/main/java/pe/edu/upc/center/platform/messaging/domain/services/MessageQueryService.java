package pe.edu.upc.center.platform.messaging.domain.services;

import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetConversationQuery;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetLastMessagesGroupedByChatQuery;

import java.util.List;

public interface MessageQueryService {
    List<Message> handle(GetLastMessagesGroupedByChatQuery query);
    List<Message> handle(GetConversationQuery query);
}
