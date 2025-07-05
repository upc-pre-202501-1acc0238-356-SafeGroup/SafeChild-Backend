package pe.edu.upc.center.platform.messaging.application.internal;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.commands.CreateMessageCommand;
import pe.edu.upc.center.platform.messaging.domain.services.MessageCommandService;
import pe.edu.upc.center.platform.messaging.infrastructure.persistence.jpa.MessageRepository;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Tutor;
import pe.edu.upc.center.platform.usermanagement.infrastructure.persistence.jpa.repositories.CaregiverRepository;
import pe.edu.upc.center.platform.usermanagement.infrastructure.persistence.jpa.repositories.TutorRepository;

@Service
public class MessageCommandServiceImpl implements MessageCommandService {

    private final MessageRepository messageRepository;
    private final TutorRepository tutorRepository;
    private final CaregiverRepository caregiverRepository;

    public MessageCommandServiceImpl(MessageRepository messageRepository, TutorRepository tutorRepository, CaregiverRepository caregiverRepository) {
        this.messageRepository = messageRepository;
        this.tutorRepository = tutorRepository;
        this.caregiverRepository = caregiverRepository;
    }

    @Override
    public Message handle(CreateMessageCommand command) {
        Tutor sender = tutorRepository.findById(command.senderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Caregiver receiver = caregiverRepository.findById(command.receiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));


        Message message = new Message(sender, receiver, command.content());
        return messageRepository.save(message);
    }
}