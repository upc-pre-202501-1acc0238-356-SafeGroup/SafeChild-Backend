package pe.edu.upc.center.platform.messaging.application.internal;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.commands.CreateMessageCommand;
import pe.edu.upc.center.platform.messaging.domain.services.MessageCommandService;
import pe.edu.upc.center.platform.messaging.infrastructure.persistence.jpa.MessageRepository;

@Service
public class MessageCommandServiceImpl implements MessageCommandService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageCommandServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
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