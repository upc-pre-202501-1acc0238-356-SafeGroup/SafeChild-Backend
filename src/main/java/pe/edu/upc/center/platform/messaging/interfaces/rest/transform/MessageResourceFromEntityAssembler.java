package pe.edu.upc.center.platform.messaging.interfaces.rest.transform;

import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.interfaces.rest.resources.MessageResource;

public class MessageResourceFromEntityAssembler {

    public static MessageResource toResourceFromEntity(Message message) {
        return new MessageResource(message.getId(), message.getSender(), message.getReceiver(), message.getContent(), message.getTimestamp());
    }
}