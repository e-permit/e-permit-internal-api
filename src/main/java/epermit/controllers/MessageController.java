package epermit.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import epermit.common.CommandResult;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @PostMapping("/created")
    public CommandResult create() {
        return CommandResult.success();
    }

    @PostMapping("/processed")
    public CommandResult process() {
        return CommandResult.success();
    }

    @PostMapping("/deleted")
    public CommandResult delete() {
        return CommandResult.success();
    }
}
