package epermit.commands;

import an.awesome.pipelinr.Command;
import lombok.Data;

@Data
public class ReceiveMessageCommand implements Command<String> {
    private String messageJws;
}
