package epermit.commands;

import an.awesome.pipelinr.Command;
import epermit.common.MessageResult;
import epermit.common.MessageType;
import lombok.Data;

@Data
public class ReceiveMessageCommand implements Command<MessageResult> {
    private String iss;

    private MessageType messageType;

    private String message;

    private String proof;
}
