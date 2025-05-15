package pe.edu.upc.center.platform.messaging.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetConversationQuery;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetLastMessagesGroupedByChatQuery;
import pe.edu.upc.center.platform.messaging.domain.services.MessageQueryService;
import pe.edu.upc.center.platform.messaging.infrastructure.persistence.jpa.repositories.MessageRepository;

import java.util.List;

@Service
public class MessageQueryServiceImpl implements MessageQueryService {

    private final MessageRepository messageRepository;

    public MessageQueryServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> handle(GetLastMessagesGroupedByChatQuery query) {
        return messageRepository.findLastMessagesPerChat(query.userId());
    }

    @Override
    public List<Message> handle(GetConversationQuery query) {
        return messageRepository.findConversation(query.userId(), query.otherUserId());
    }
}
