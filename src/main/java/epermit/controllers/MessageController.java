package epermit.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import epermit.commands.ReceiveMessageCommand;
import epermit.common.MessageResult;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @PostMapping()
    public MessageResult receive(@RequestBody ReceiveMessageCommand command) {
        //
        return MessageResult.success("");
    }
}
