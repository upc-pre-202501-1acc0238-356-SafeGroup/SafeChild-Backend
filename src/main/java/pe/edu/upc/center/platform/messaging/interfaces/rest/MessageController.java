package pe.edu.upc.center.platform.messaging.interfaces.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.messaging.domain.model.aggregates.Message;
import pe.edu.upc.center.platform.messaging.domain.model.commands.CreateMessageCommand;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetConversationQuery;
import pe.edu.upc.center.platform.messaging.domain.model.queries.GetLastMessagesGroupedByChatQuery;
import pe.edu.upc.center.platform.messaging.domain.services.MessageCommandService;
import pe.edu.upc.center.platform.messaging.domain.services.MessageQueryService;


import java.util.List;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@Tag(name = "Messages", description = "Message Management Endpoint")
@RequestMapping(path = "/api/v1/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageCommandService messageCommandService;
    private final MessageQueryService messageQueryService;

    public MessageController(MessageCommandService messageCommandService, MessageQueryService messageQueryService) {
        this.messageCommandService = messageCommandService;
        this.messageQueryService = messageQueryService;
    }

    @GetMapping("/chats/{userId}")
    public List<Message> getChats(@PathVariable Long userId) {
        var query = new GetLastMessagesGroupedByChatQuery(userId);
        return messageQueryService.handle(query);
    }

    @GetMapping("/{userId}/{otherUserId}")
    public List<Message> getConversation(@PathVariable Long userId, @PathVariable Long otherUserId) {
        var query = new GetConversationQuery(userId, otherUserId);
        return messageQueryService.handle(query);
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestBody CreateMessageCommand request) {
        var command = new CreateMessageCommand(request.senderId(), request.receiverId(), request.content());
        return messageCommandService.handle(command);
    }
}
