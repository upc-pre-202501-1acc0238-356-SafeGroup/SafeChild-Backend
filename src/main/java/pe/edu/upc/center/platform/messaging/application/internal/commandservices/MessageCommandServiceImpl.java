package pe.edu.upc.center.platform.messaging.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.commands.CreateMessageCommand;
import pe.edu.upc.center.platform.messaging.domain.services.MessageCommandService;
import pe.edu.upc.center.platform.messaging.infrastructure.persistence.jpa.repositories.MessageRepository;
import pe.edu.upc.center.platform.user.domain.model.aggregates.User;
import pe.edu.upc.center.platform.user.infrastructure.persistence.jpa.repositories.UserRepositoryM;

@Service
public class MessageCommandServiceImpl implements MessageCommandService {

    private final MessageRepository messageRepository;
    private final UserRepositoryM userRepository;

    public MessageCommandServiceImpl(MessageRepository messageRepository, UserRepositoryM userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message handle(CreateMessageCommand command) {
        User sender = userRepository.findById(command.senderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(command.receiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));


        Message message = new Message(sender, receiver, command.content());
        return messageRepository.save(message);
    }
}
