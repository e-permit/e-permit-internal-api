package epermit.data.messagehandlers;

import org.springframework.transaction.annotation.Transactional;
import an.awesome.pipelinr.Command;
import epermit.data.repositories.PermitRepository;
import epermit.messages.CreatePermitMessage;
import lombok.SneakyThrows;

public class CreatePermitMessageHandler
        implements Command.Handler<CreatePermitMessage, String> {

    private final PermitRepository repository;

    public CreatePermitMessageHandler(PermitRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public String handle(CreatePermitMessage m) {
       return "";
    }
}

