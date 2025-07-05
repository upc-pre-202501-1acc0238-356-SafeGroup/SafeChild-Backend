package pe.edu.upc.center.platform.messaging.domain.model.commands;

public record CreateMessageCommand(Long senderId, Long receiverId, String content) {
}
